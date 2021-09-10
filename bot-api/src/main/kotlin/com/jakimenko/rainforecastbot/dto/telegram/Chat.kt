package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Chat(
    var id: Int,
    var first_name: String?,
    var last_name: String?,
    var username: String?,
    var type: String?
) {
    constructor(): this(0, "", "", "", "")
}
