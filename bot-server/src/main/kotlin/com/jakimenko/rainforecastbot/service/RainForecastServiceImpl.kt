package com.jakimenko.rainforecastbot.service

import com.google.gson.Gson
import com.jakimenko.rainforecastbot.dto.telegram.Location
import com.jakimenko.rainforecastbot.dto.telegram.Update
import com.jakimenko.rainforecastbot.openweathermap.dto.CurrentWeatherInCity
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.DeleteWebhook
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.request.SetWebhook
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class RainForecastServiceImpl(
    val bot: TelegramBot = TelegramBot(System.getenv("TGTOKEN"))
): RainForecastService {
    companion object {
        val logger = LoggerFactory.getLogger(RainForecastServiceImpl::class.java)
    }

    override fun callback(update: Update) {
        try {
            logger.info("callback update: $update")
            val weather: CurrentWeatherInCity = getCurrentWeather(update.message!!.location!!)
            val responseMessage = buildResponseMessage(weather)
            val response = bot.execute(SendMessage(update.message!!.chat!!.id, responseMessage))
            println("response = ${response}")
        } catch (e: Exception) {
            bot.execute(SendMessage(update.message!!.chat!!.id, "Ошибка получения информации о погоде"))
        }
    }

    private fun buildResponseMessage(weather: CurrentWeatherInCity): String {
        var message = "Текущая погода в ${weather.name}:\n"
        message += weather.weather.first().description
        message += "температура ${weather.main.temp}\n"
        message += "ощущается ${weather.main.feels_like}\n"
        message += "ветер ${weather.wind.speed}"
        return message
    }

    private fun getCurrentWeather(location: Location): CurrentWeatherInCity {
        return runBlocking {
            val URL = "https://api.openweathermap.org/data/2.5/weather?lat=${location.latitude}&lon=${location.longitude}&units=metric&lang=ru&appid=${System.getenv("OPENWEATHERMAP_APP_ID")}"

            val client = HttpClient(Java)
            val httpResponse: HttpResponse = client.get(URL)
            println("httpResponse = ${httpResponse.status}")
            val currentWeather = Gson().fromJson(httpResponse.receive<String>(), CurrentWeatherInCity::class.java)
            currentWeather
        }
    }

    override fun register(): String {
        val request: SetWebhook = SetWebhook()
            .url(System.getenv("WEBHOOK_URL") + "4875293485AAGo77nj9TrqBRH2EZc8BvVitDKAMVZFX32CQ")

        val response = bot.execute(request)
        return if (response.isOk)
            "Webhook was registered successful" else "Error webhook regitering"
    }

    override fun unsetWebhooks(): String {
        val request: DeleteWebhook = DeleteWebhook().dropPendingUpdates(true)
        val response = bot.execute(request)
        return if (response.isOk)
            "Webhook was unregistered successful" else "Error webhook regitering"
    }
}
