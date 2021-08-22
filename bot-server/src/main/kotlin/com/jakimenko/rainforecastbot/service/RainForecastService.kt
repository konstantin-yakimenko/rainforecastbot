package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.telegram.Update

interface RainForecastService {
    fun callback(update: Update)
    fun register(): String
    fun unsetWebhooks(): String
}
