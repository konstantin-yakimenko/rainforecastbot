package com.jakimenko.rainforecastbot.dto.openweathermap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)
