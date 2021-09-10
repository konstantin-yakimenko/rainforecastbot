package com.jakimenko.rainforecastbot.dto.telegram

data class Location(
    val longitude: Float,
    val latitude: Float,
    val horizontal_accuracy: Float?,
    val live_period: Int?,
    val heading: Int?,
    val proximity_alert_radius: Int?
) {
    constructor(): this(0f, 0f, null, null, null, null)
}
