package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageEntity(
    val offset: Int?,
    val length: Int?,
    val type: String?
)
