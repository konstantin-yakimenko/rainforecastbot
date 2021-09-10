package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Location(
    val longitude: Float,
    val latitude: Float,
    val horizontal_accuracy: Float?,
    val live_period: Int?,
    val heading: Int?,
    val proximity_alert_radius: Int?
)
