package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.openweathermap.WeatherInfo

interface WeatherApiCall<T: WeatherInfo> {
    fun <T: WeatherInfo> callWeatherApi(url: String, clazz: Class<T>): T
}
