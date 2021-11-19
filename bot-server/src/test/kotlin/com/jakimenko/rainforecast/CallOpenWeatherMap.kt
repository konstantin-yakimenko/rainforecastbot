package com.jakimenko.rainforecast

import com.jakimenko.rainforecastbot.dto.openweathermap.CurrentWeatherInCity
import com.jakimenko.rainforecastbot.dto.openweathermap.OnecallResponse
import com.jakimenko.rainforecastbot.service.WeatherApiCallImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.Test


class CallOpenWeatherMap {

/*
    @Test
    fun test() {
        runBlocking {

            println("================ start")
            val res = async {
                val client: OkHttpClient = OkHttpClient.Builder()
                    .build()

                // CurrentWeatherInCity::class.java
                // OnecallResponse::class.java
                val clazz = CurrentWeatherInCity::class.java
//            val clazz = OnecallResponse::class.java
//            val url = "https://api.openweathermap.org/data/2.5/onecall?lat=55.751442&lon=37.615569&exclude=daily,alerts&units=metric&lang=ru&appid="
                val url = "https://api.openweathermap.org/data/2.5/weather?lat=55.751442&lon=37.615569&units=metric&lang=ru&appid="

                val request: Request = Request.Builder()
                    .url(url)
                    .build()
                val call: Call = client.newCall(request)
                val response: Response = call.execute()
                return@async WeatherApiCallImpl.mapper.readValue(response.body?.bytes()!!, clazz)
            }.await()

            println("================")
            println("res = ${res}")
            println("================ finish")
        }
    }
*/

}
