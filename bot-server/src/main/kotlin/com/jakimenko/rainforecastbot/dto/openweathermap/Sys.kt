package com.jakimenko.rainforecastbot.dto.openweathermap

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
