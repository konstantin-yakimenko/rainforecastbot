package com.jakimenko.rainforecastbot.service

import com.google.gson.Gson
import com.jakimenko.rainforecastbot.dto.telegram.Location
import com.jakimenko.rainforecastbot.dto.telegram.Update
import com.jakimenko.rainforecastbot.openweathermap.dto.CurrentWeatherInCity
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.KeyboardButton
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup
import com.pengrad.telegrambot.request.DeleteWebhook
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.request.SetWebhook
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

const val API_URL = "https://api.openweathermap.org/data/2.5/weather?"

@Service
class RainForecastServiceImpl(
    val bot: TelegramBot = TelegramBot(System.getenv("TGTOKEN")),
    val openweathermapAppId: String = System.getenv("OPENWEATHERMAP_APP_ID"),
    val gson: Gson = Gson()
): RainForecastService {
    companion object {
        val logger = LoggerFactory.getLogger(RainForecastServiceImpl::class.java)
    }

    @DelicateCoroutinesApi
    override fun callback(update: Update) {
        GlobalScope.launch {
            try {
                withTimeout(5000) {
                    executeCallback(
                        update.message!!.location,
                        update.message!!.text,
                        update.message!!.chat!!.id
                    )
                }
            } catch (e: Exception) {
                logger.error("Error", e)
                sendMessageToUser(update.message!!.chat!!.id, "Ошибка получения информации о погоде")
            }
        }
    }

    private fun executeCallback(location: Location?, message: String?, chatId: Int) {
        val weather: CurrentWeatherInCity = getCurrentWeather(location, message)
        val responseMessage = buildResponseMessage(weather)
        sendMessageToUser(chatId, responseMessage)
    }

    private fun sendMessageToUser(chatId: Int, message: String) {
        bot.execute(SendMessage(chatId, message)
            .replyMarkup(
                ReplyKeyboardMarkup(
                    KeyboardButton("")
                        .requestLocation(true)))
        )
    }

    private fun requestLocation(chatId: Int) {
        bot.execute(SendMessage(chatId, "Для определения погоды")
            .replyMarkup(
                ReplyKeyboardMarkup(
                    KeyboardButton("Отправьте логакцию")
                        .requestLocation(true)))
        )
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
            HttpClient(Java) {
                engine {
                    threadsCount = 1
                    pipelining = true
                }
            }
                .use {
                    withTimeout(1000) {
                        val httpResponse: HttpResponse = it.get(buildUrl(location, city))
                        val currentWeather = gson.fromJson(httpResponse.receive<String>(), CurrentWeatherInCity::class.java)
                        return@withTimeout currentWeather
                    }
                }
        }
    }

    private fun buildUrl(location: Location?, city: String?): String {
        if (location != null) {
            return API_URL + "lat=${location.latitude}&lon=${location.longitude}&units=metric&lang=ru&appid=$openweathermapAppId"
        } else if (city != null) {
            return API_URL + "q=${city}&units=metric&lang=ru&appid=$openweathermapAppId"
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
