package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Update(
    var update_id: Int,
    var message: Message?
) {
    constructor(): this(0, Message())
}
