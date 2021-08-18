package com.jakimenko.rainforecastbot.service

import com.pengrad.telegrambot.model.Update

interface RainForecastService {
    fun callback(update: String)
    fun register(): String
}
