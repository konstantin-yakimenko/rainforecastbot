package com.jakimenko.rainforecastbot.server

import com.jakimenko.rainforecastbot.server.handler.ApiHandler
import com.jakimenko.rainforecastbot.server.handler.RegisterHandler
import com.jakimenko.rainforecastbot.server.handler.UnregisterHandler
import io.netty.handler.codec.http.FullHttpRequest
import io.netty.handler.codec.http.HttpMethod
import mu.KLogging
import org.springframework.stereotype.Component


@Component
class PathHandlerProvider(
    val apiHandler: ApiHandler,
    val registerHandler: RegisterHandler,
    val unregisterHandler: UnregisterHandler
) {
    companion object: KLogging()

    fun getHandler(request: FullHttpRequest): HttpHandler? {
        var uri = request.uri()
        if (uri.contains("?")) {
            uri = uri.substring(0, uri.indexOf("?"))
        }
        val method = request.method()
        if (method == HttpMethod.POST && uri == System.getenv("TGTOKEN")) {
            return apiHandler
        } else if (method == HttpMethod.GET && uri == "register") {
            return registerHandler
        } else if (method == HttpMethod.GET && uri == "unregister") {
            return unregisterHandler
        }
        logger.error { "Not found handler for request: ${uri}" }
        return null
    }
}
