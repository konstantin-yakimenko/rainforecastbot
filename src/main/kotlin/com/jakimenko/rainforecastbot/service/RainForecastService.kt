package com.jakimenko.rainforecastbot.service

import com.pengrad.telegrambot.model.Update

interface RainForecastService {
    fun callback(updatelist: List<Update?>?)
    fun register(): String
}
