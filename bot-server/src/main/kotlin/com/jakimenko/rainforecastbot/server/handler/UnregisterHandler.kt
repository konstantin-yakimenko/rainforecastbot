package com.jakimenko.rainforecastbot.server.handler

import com.jakimenko.rainforecastbot.server.HttpHandler
import com.jakimenko.rainforecastbot.service.RainForecastService
import io.netty.handler.codec.http.FullHttpRequest
import org.springframework.stereotype.Component

@Component
class UnregisterHandler(
    val rainForecastService: RainForecastService
): HttpHandler {
    override fun handleRequest(request: FullHttpRequest?) =
        rainForecastService.unsetWebhooks()
}
