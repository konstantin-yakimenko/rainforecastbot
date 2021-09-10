package com.jakimenko.rainforecastbot.dto.openweathermap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class OnecallResponse(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current,
    val minutely: Array<Minute>?,
    val hourly: Array<Hour>?
): WeatherInfo

