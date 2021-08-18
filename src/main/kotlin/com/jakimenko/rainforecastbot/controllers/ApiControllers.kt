package com.jakimenko.rainforecastbot.controllers

import com.jakimenko.rainforecastbot.dto.Update
import com.jakimenko.rainforecastbot.service.RainForecastService
import com.pengrad.telegrambot.model.Message
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

//    @PostMapping("callback")
//    fun postCallback(@RequestBody message: Message) = ResponseEntity.ok(rainForecastService.callback(message))

    @PostMapping("callback")
    fun postCallback(@RequestBody message: Update): ResponseEntity<String> {
        println("message = ${message}")
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("register")
    fun register() = ResponseEntity.ok(rainForecastService.register())

}
