package com.jakimenko.rainforecastbot.openweathermap.dto

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
