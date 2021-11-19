package com.jakimenko.rainforecastbot.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jakimenko.rainforecastbot.dto.openweathermap.WeatherInfo
import kotlinx.coroutines.async
import mu.KLogging
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response


class WeatherApiCallImpl<T: WeatherInfo>: WeatherApiCall<T> {
    companion object: KLogging() {
        val mapper = jacksonObjectMapper()
    }

    override suspend fun <T: WeatherInfo> callWeatherApi(url: String, clazz: Class<T>, httpClient: HttpClient): T {
        return scope.async {
            val request: Request = Request.Builder()
                .url(url)
                .build()
            val call: Call = httpClient.client.newCall(request)
            val response: Response = call.execute()
            return@async mapper.readValue(response.body?.bytes()!!, clazz)
        }.await()
    }
}
