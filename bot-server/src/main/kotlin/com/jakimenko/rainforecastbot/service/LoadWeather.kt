package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.telegram.Location

interface LoadWeather {
    fun load(location: Location?, text: String?): String
}
