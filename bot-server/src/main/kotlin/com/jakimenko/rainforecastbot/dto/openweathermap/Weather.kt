package com.jakimenko.rainforecastbot.dto.openweathermap

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
