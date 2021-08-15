package com.jakimenko.rainforecastbot.controlers;

public class BaseResponse {
    private final int code;
    private final String message;

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
