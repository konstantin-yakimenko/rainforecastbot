package com.jakimenko.rainforecastbot.dto.openweathermap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Rain(
    @JsonProperty("1h") val hour: Float,
    @JsonProperty("3h") val treeHours: Float?
)
