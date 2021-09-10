package com.jakimenko.rainforecastbot.dto.telegram

data class MessageEntity(
    var offset: Int?,
    var length: Int?,
    var type: String?
) {
    constructor(): this(null, null, null)
}
