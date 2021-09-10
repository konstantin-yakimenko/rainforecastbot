package com.jakimenko.rainforecastbot.dto.telegram

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    var id: Int,
    @JsonProperty("is_bot")
    var is_bot: Boolean?,
    var first_name: String?,
    var last_name: String?,
    var username: String?,
    var language_code: String?
) {
    constructor(): this(0, true, null, null, null, null)
}
