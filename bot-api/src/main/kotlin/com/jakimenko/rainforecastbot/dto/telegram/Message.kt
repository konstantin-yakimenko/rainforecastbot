package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Message(
    var message_id: Int,
    var from: User?,
    var chat: Chat?,
    var date: Int,
    var text: String?,
    var entities: Array<MessageEntity>?,
    var location: Location?
) {
    constructor(): this(0, User(), Chat(), 0, null, arrayOf(), null)
}
