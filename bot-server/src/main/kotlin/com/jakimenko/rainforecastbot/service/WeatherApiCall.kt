package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.openweathermap.WeatherInfo

interface WeatherApiCall<T: WeatherInfo> {
    suspend fun <T: WeatherInfo> callWeatherApi(url: String, clazz: Class<T>, httpClient: HttpClient): T
}
