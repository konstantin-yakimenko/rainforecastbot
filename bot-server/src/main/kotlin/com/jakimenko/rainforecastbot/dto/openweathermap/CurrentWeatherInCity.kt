package com.jakimenko.rainforecastbot.dto.openweathermap

data class CurrentWeatherInCity(
    val coord: Coord,
    val weather: Array<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind?,
    val clouds: Clouds?,
    val rain: Rain?,
    val snow: Snow?,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
): WeatherInfo
