package com.jakimenko.rainforecastbot.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jakimenko.rainforecastbot.dto.openweathermap.WeatherInfo
import mu.KLogging
import okhttp3.Request


class WeatherApiCallImpl<T: WeatherInfo>: WeatherApiCall<T> {
    companion object: KLogging() {
        val mapper = jacksonObjectMapper()
    }

    override suspend fun <T: WeatherInfo> callWeatherApi(url: String, clazz: Class<T>, httpClient: HttpClient): T {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        return httpClient
            .client
            .newCall(request)
            .execute()
            .use {
                mapper.readValue(it.body?.bytes()!!, clazz)
            }
    }
}
