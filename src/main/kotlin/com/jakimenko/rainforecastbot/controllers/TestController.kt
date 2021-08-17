package com.jakimenko.rainforecastbot.controllers

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class TestController {

    @GetMapping("test")
    fun testMethod() = "test method"
        // System.getenv("TGTOKEN")

    @GetMapping
    fun rootTestMethod() = "Root test method"

    // http://localhost:8080/double?msg=mytext
    @GetMapping("double")
    fun doubbledMsg(@RequestParam("msg") msg: String) = msg+msg
}
