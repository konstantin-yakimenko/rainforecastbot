package com.jakimenko.rainforecastbot.service

import okhttp3.OkHttpClient
import org.springframework.stereotype.Component

@Component
class HttpClient(
    val client: OkHttpClient = OkHttpClient.Builder()
        .build()
)
