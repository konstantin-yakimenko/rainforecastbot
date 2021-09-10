package com.jakimenko.rainforecastbot.dto.openweathermap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Current(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Float,
    val feels_like: Float,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Float,
    val uvi: Float?,
    val clouds: Int?,
    val visibility: Int,
    val wind_speed: Float,
    val wind_deg: Int,
    val wind_gust: Float,
    val rain: Rain?,
    val snow: Snow?,
    val weather: Array<Weather>
)
