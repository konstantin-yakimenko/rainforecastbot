package com.jakimenko.rainforecastbot.dto.telegram

data class Chat(
    val id: Int,
    val first_name: String?,
    val last_name: String?,
    val username: String?,
    val type: String?
)
