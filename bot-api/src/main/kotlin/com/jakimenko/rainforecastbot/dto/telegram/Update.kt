package com.jakimenko.rainforecastbot.dto.telegram

data class Update(
    val update_id: Int,
    val message: Message?
)
