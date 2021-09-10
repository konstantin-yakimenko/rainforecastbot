package com.jakimenko.rainforecastbot.server.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakimenko.rainforecastbot.dto.telegram.Update
import com.jakimenko.rainforecastbot.server.HttpHandler
import com.jakimenko.rainforecastbot.service.RainForecastService
import io.netty.handler.codec.http.FullHttpRequest
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class ApiHandler(
    val rainForecastService: RainForecastService
): HttpHandler {

    companion object: KLogging() {
        val mapper = ObjectMapper()
    }

    override fun handleRequest(request: FullHttpRequest?): Any {
        if (request == null) {
            return "Not found"
        }

        try {
            val byteArray = readByteArray(request)
            val update = mapper.readValue(byteArray, Update::class.java)
            rainForecastService.callback(update)
            return "ok"
        } catch (e: Exception) {
            logger.error { "Error: ${e}" }
            throw e
        }
    }

    fun readByteArray(request: FullHttpRequest): ByteArray {
        return try {
            val buf = request.content()
            val bytes = ByteArray(buf.readableBytes())
            buf.readBytes(bytes)
            bytes
        } catch (e: Exception) {
            logger.error { "Error: ${e}" }
            throw e
        }
    }
}
