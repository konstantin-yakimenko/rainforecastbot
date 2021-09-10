package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageEntity(
    var offset: Int?,
    var length: Int?,
    var type: String?
) {
    constructor(): this(0, 0, "")
}
