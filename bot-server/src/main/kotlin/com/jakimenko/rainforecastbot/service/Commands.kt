package com.jakimenko.rainforecastbot.service

enum class Commands(
    val command: String
) {
    COMMAND_FORECAST("/forecast"),
    COMMAND_START("/start");

    companion object {
        fun any(command: String) = values().any { it.command == command }
    }
}
