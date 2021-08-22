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
            val weather: CurrentWeatherInCity = getCurrentWeather(update.message!!.location, update.message!!.text)
            val responseMessage = buildResponseMessage(weather)
            val response = bot.execute(SendMessage(update.message!!.chat!!.id, responseMessage))
            println("response = ${response}")
        } catch (e: Exception) {
            bot.execute(SendMessage(update.message!!.chat!!.id, "Ошибка получения информации о погоде"))
        }
    }

    private fun buildResponseMessage(weather: CurrentWeatherInCity): String {
        var message = "Текущая погода в ${weather.name}:\n"
        message += weather.weather.first().description+"\n"
        message += "температура ${weather.main.temp}\n"
        message += "ощущается как ${weather.main.feels_like}\n"
        if (weather.clouds != null) {
            message += "облачность ${weather.clouds.all} %\n"
        }
        if (weather.wind != null) {
            message += "ветер ${weather.wind.speed} м/с\n"
        }
        if (weather.rain != null) {
            message += "дождь ${weather.rain.hour} мм\n"
        }
        if (weather.snow != null) {
            message += "снег ${weather.snow.hour} мм\n"
        }
        message += "восход ${calcDate(weather.sys.sunrise, weather.timezone)}\n"
        message += "закат  ${calcDate(weather.sys.sunset, weather.timezone)}"
        return message
    }

    private fun calcDate(time: Int, timezone: Int): String {
        val timeWithZone = (time + timezone).toLong()
        return java.time.format.DateTimeFormatter.ISO_INSTANT
            .format(java.time.Instant.ofEpochSecond(timeWithZone))
            .toString()
            .replace("Z", "")
            .replace("T", " ")
    }

    private fun getCurrentWeather(location: Location?, city: String?): CurrentWeatherInCity {
        return runBlocking {
            val client = HttpClient(Java)
            val httpResponse: HttpResponse = client.get(buildUrl(location, city))
            val currentWeather = Gson().fromJson(httpResponse.receive<String>(), CurrentWeatherInCity::class.java)
            currentWeather
        }
    }

    private fun buildUrl(location: Location?, city: String?): String {
        if (location != null) {
            return "https://api.openweathermap.org/data/2.5/weather?lat=${location.latitude}&lon=${location.longitude}&units=metric&lang=ru&appid=${System.getenv("OPENWEATHERMAP_APP_ID")}"
        } else if (city != null) {
            return "https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&lang=ru&appid=${System.getenv("OPENWEATHERMAP_APP_ID")}"
        } else {
            throw IllegalArgumentException("Некорректные входные данные")
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
