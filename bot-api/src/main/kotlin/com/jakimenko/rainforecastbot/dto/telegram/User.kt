package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    var id: Int?,
    var is_bot: Boolean?,
    var first_name: String?,
    var last_name: String?,
    var username: String?,
    var language_code: String?
) {
    constructor(): this(0, true, "", "", "", "")
}
