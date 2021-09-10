package com.jakimenko.rainforecastbot.server

import io.netty.handler.codec.http.FullHttpRequest

interface HttpHandler {
    fun handleRequest(request: FullHttpRequest?): Any
}
