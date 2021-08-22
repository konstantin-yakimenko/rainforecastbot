package com.jakimenko.rainforecastbot.api

import com.jakimenko.rainforecastbot.dto.telegram.Update
import org.springframework.http.ResponseEntity

interface ApiControllers {

    fun rootTestMethod(): String

    fun postCallback(update: Update): ResponseEntity<Unit?>
}
