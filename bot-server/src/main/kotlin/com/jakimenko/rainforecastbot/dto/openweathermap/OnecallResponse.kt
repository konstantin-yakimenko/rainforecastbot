package com.jakimenko.rainforecastbot.dto.openweathermap


data class OnecallResponse(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current,
    val minutely: Array<Minute>,
    val hourly: Array<Hour>
): WeatherInfo

