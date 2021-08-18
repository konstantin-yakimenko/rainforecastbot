package com.jakimenko.rainforecastbot.service

import com.pengrad.telegrambot.model.Message

interface RainForecastService {
    fun callback(message: Message)
    fun register(): String
}
