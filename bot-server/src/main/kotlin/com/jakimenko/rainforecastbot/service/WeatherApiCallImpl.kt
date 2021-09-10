package com.jakimenko.rainforecastbot.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jakimenko.rainforecastbot.dto.openweathermap.WeatherInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import mu.KLogging


class WeatherApiCallImpl<T: WeatherInfo>: WeatherApiCall<T> {
    companion object: KLogging() {
        val mapper = jacksonObjectMapper()
    }

    override fun <T: WeatherInfo> callWeatherApi(url: String, clazz: Class<T>): T {
        return runBlocking {
            HttpClient(Java) {
                engine {
                    threadsCount = 1
                    pipelining = true
                }
            }
                .use {
                    withTimeout(1000) {
                        val httpResponse: HttpResponse = it.get(url)
                        val currentWeather = mapper.readValue(httpResponse.receive<String>(), clazz)
                        return@withTimeout currentWeather
                    }
                }
        }
    }
}
