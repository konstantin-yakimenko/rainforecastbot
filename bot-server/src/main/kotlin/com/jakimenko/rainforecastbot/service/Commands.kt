package com.jakimenko.rainforecastbot.service

enum class Commands(
    val command: String
) {
    COMMAND_FORECAST("/forecast"),
    COMMAND_START("/start");

    companion object {
        fun oneOfThem(command: String): Boolean {
            values().forEach {
                if (it.command == command) {
                    return true
                }
            }
            return false
        }
    }
}
