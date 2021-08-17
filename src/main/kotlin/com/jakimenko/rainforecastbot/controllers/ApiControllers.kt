package com.jakimenko.rainforecastbot.controllers

import com.jakimenko.rainforecastbot.service.RainForecastService
import com.pengrad.telegrambot.model.Update
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class ApiControllers(
    val rainForecastService: RainForecastService
) {

    @GetMapping("test")
    fun testMethod() = "test method"

    @GetMapping
    fun rootTestMethod() = "Root test method"

    @GetMapping("callback")
    fun getCallback(@RequestBody update: Update?) = ResponseEntity.ok(rainForecastService.callback(update))

    @PostMapping("callback")
    fun postCallback(@RequestBody update: Update?) = ResponseEntity.ok(rainForecastService.callback(update))

    @GetMapping("register")
    fun register() = ResponseEntity.ok(rainForecastService.register())

}
