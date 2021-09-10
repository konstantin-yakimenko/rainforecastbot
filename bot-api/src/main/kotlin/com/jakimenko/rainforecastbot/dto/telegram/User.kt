package com.jakimenko.rainforecastbot.dto.telegram

data class User(
    var id: Int,
    var is_bot: Boolean?,
    var first_name: String?,
    var last_name: String?,
    var username: String?,
    var language_code: String?
) {
    constructor(): this(0, true, null, null, null, null)
}
