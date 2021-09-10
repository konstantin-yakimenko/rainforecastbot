package com.jakimenko.rainforecastbot.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpRequestDecoder
import io.netty.handler.codec.http.HttpResponseEncoder
import org.springframework.stereotype.Component


@Component
class HttpChannelInitializer(
    val httpControllerHandler: HttpControllerHandler
) : ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel?) {
        val pipeline: ChannelPipeline = ch!!.pipeline()
        pipeline.addLast(HttpRequestDecoder())
        pipeline.addLast(HttpObjectAggregator(Int.MAX_VALUE)) // 1048576
        pipeline.addLast(HttpResponseEncoder())
        pipeline.addLast(httpControllerHandler)
    }
}
