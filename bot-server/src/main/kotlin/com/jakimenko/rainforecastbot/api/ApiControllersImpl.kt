package com.jakimenko.rainforecastbot.api

import com.jakimenko.rainforecastbot.dto.telegram.Update
import com.jakimenko.rainforecastbot.service.RainForecastService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class ApiControllersImpl(
    val rainForecastService: RainForecastService
): ApiControllers {

    @GetMapping
    override fun rootTestMethod() = "Root test method"

    @PostMapping("4875293485AAGo77nj9TrqBRH2EZc8BvVitDKAMVZFX32CQ")
    override fun postCallback(@RequestBody update: String): ResponseEntity<Unit?> {
        println("update = ${update}")
        return ResponseEntity.ok(Unit)
    }

//     override fun postCallback(@RequestBody update: Update) = ResponseEntity.ok(rainForecastService.callback(update))
//    @GetMapping("register")
//    fun register() = ResponseEntity.ok(rainForecastService.register())

//    @GetMapping("unregister")
//    fun unregister() = ResponseEntity.ok(rainForecastService.unsetWebhooks())

}
