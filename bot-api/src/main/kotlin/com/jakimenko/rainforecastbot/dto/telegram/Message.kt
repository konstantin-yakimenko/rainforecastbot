package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Message(
    val message_id: Int,
    val from: User?,
    val chat: Chat?,
    val date: Int,
    val text: String?,
    val entities: Array<MessageEntity>?,
    val location: Location?
)
