package com.jakimenko.rainforecastbot.dto.telegram

data class Location(
    var longitude: Float,
    var latitude: Float,
    var horizontal_accuracy: Float?,
    var live_period: Int?,
    var heading: Int?,
    var proximity_alert_radius: Int?
) {
    constructor(): this(0f, 0f, null, null, null, null)
}
