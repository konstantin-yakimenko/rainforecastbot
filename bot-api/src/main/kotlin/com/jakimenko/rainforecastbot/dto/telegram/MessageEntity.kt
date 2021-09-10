package com.jakimenko.rainforecastbot.dto.telegram

data class MessageEntity(
    val offset: Int?,
    val length: Int?,
    val type: String?
) {
    constructor(): this(null, null, null)
}
