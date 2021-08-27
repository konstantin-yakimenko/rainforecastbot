package com.jakimenko.rainforecastbot.service

import com.google.gson.Gson
import com.jakimenko.rainforecastbot.dto.openweathermap.WeatherInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class WeatherApiCallImpl<T: WeatherInfo>(
    val gson: Gson = Gson()
): WeatherApiCall<T> {

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
                        val currentWeather = gson.fromJson(httpResponse.receive<String>(), clazz)
                        return@withTimeout currentWeather
                    }
                }
        }
    }
}
