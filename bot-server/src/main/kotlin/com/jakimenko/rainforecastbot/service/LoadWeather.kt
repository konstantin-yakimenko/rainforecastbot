package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.telegram.Location

interface LoadWeather {
    suspend fun load(location: Location?, text: String?, httpClient: HttpClient): String
}
