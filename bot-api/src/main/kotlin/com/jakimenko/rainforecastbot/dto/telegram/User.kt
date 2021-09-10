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
    constructor(): this(id = 0, is_bot = true, first_name = null, last_name = null, username = null, language_code = null)
    constructor(id: Int, first_name: String?, last_name: String?, username: String?, language_code: String?):
        this(id = id, is_bot = null, first_name = first_name, last_name = last_name, username = username, language_code = language_code)
}
