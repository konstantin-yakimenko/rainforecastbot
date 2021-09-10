package com.jakimenko.rainforecastbot.dto.telegram

data class Message(
    var message_id: Int,
    var from: User?,
    var chat: Chat?,
    var date: Int,
    var text: String?,
    var entities: Array<MessageEntity>?,
    var location: Location?
) {
    constructor(): this(0, null, null, 0, null, null, null)
}
