package com.jakimenko.rainforecastbot.server

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.buffer.Unpooled.wrappedBuffer
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.*
import io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN
import io.netty.util.AsciiString
import mu.KLogging
import org.springframework.stereotype.Component

@ChannelHandler.Sharable
@Component
class HttpControllerHandler(
    val pathHandlerProvider: PathHandlerProvider
) : SimpleChannelInboundHandler<FullHttpRequest>(true) {

    companion object: KLogging() {
        val jacksonObjectMapper:ObjectMapper = ObjectMapper()
    }

    @Throws(Exception::class)
    override fun channelRead0(ctx: ChannelHandlerContext, request: FullHttpRequest) {
        var responseStatus = HttpResponseStatus.OK
        var responseBody: String? = ""
        var mimeType = HttpHeaderValues.APPLICATION_JSON
        try {
            val handler: HttpHandler? = pathHandlerProvider.getHandler(request)
            if (handler == null) {
                writeResponse(ctx, HttpResponseStatus.NOT_FOUND, TEXT_PLAIN, "Not found.")
                return
            }
            val response: Any = handler.handleRequest(request)
            if (response is String) {
                responseBody = response
            } else {
                responseBody = toJson(response)
            }
        } catch (e: Exception) {
            responseStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR
            responseBody = if (e.message == null) "" else e.message
            mimeType = TEXT_PLAIN
        }
        writeResponse(ctx, responseStatus, mimeType, responseBody)
    }

    fun toJson(`object`: Any): String? {
        return try {
            jacksonObjectMapper.writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            logger.error { "Error while conert to json: ${e}" }
            null
        }
    }

    fun writeResponse(
        ctx: ChannelHandlerContext,
        status: HttpResponseStatus,
        mimeType: AsciiString,
        body: String?
    ) {
        val buf = wrappedBuffer(body!!.toByteArray())
        val response = DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf)
        response.headers()[HttpHeaderNames.CONTENT_LENGTH] = buf.readableBytes()
        response.headers()[HttpHeaderNames.CONTENT_TYPE] = "$mimeType; charset=UTF-8"
        HttpUtil.setKeepAlive(response, true)
        ctx.writeAndFlush(response)
    }

    @Throws(Exception::class)
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        logger.error { "Something went wrong: ${cause}" }
        writeResponse(
            ctx,
            HttpResponseStatus.INTERNAL_SERVER_ERROR,
            TEXT_PLAIN,
            if (cause.message == null) "" else cause.message
        )
    }

}
