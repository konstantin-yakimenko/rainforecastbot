package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.Update

interface RainForecastService {
    fun callback(update: Update)
    fun register(): String
}
