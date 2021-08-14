package com.jakimenko.rainforecastbot.contollers

import com.jakimenko.rainforecastbot.config.ApiVersion.API_VERSION
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestContorller {

    @GetMapping
    fun mainTest() =
        BaseResponse(200, "main, this is success")

    @GetMapping("/test")
    fun testFunction() =
        BaseResponse(200, "ok, this is success")
}

data class BaseResponse(
    val code: Int,
    val message: String
)
