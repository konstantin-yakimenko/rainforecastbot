package com.jakimenko.rainforecastbot.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/")
    public BaseResponse zeroTestMethod() {
        return new BaseResponse(200, "main test method");
    }

    @GetMapping("test")
    public BaseResponse testMethod() {
        return new BaseResponse(200, "test method");
    }
}
