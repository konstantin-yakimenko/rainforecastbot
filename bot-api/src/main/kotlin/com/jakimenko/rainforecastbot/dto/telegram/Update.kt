package com.jakimenko.rainforecastbot.dto.telegram

data class Update(
    var update_id: Int,
    var message: Message?
) {
    constructor(): this(0, Message())
}
