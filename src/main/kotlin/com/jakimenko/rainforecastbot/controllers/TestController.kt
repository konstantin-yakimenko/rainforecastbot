package com.jakimenko.rainforecastbot.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class TestController {

    @GetMapping("test")
    fun testMethod() = "Test main method"

    @GetMapping
    fun rootTestMethod() = "Root test method"
}
