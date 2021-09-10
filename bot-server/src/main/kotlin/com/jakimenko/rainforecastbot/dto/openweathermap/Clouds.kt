package com.jakimenko.rainforecastbot.dto.openweathermap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Clouds(
    val all: Int
)
