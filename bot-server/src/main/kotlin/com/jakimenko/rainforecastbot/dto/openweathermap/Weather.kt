package com.jakimenko.rainforecastbot.dto.openweathermap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
