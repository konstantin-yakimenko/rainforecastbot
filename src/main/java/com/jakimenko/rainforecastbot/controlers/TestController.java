package com.jakimenko.rainforecastbot.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    public BaseResponse testMethod() {
        return new BaseResponse(200, "main test method");
    }
}
