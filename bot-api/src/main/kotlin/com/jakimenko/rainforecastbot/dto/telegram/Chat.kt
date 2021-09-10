package com.jakimenko.rainforecastbot.dto.telegram

data class Chat(
    var id: Int,
    var first_name: String?,
    var last_name: String?,
    var username: String?,
    var type: String?
) {
    constructor(): this(0, null, null, null, null)
}
