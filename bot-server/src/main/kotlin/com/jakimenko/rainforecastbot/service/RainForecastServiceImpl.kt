package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.telegram.Location
import com.jakimenko.rainforecastbot.dto.telegram.Update
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.KeyboardButton
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup
import com.pengrad.telegrambot.request.DeleteWebhook
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.request.SetWebhook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.concurrent.Executors


const val API_URL = "https://api.openweathermap.org/data/2.5/"

val mydisspatcher = Executors
    .newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1)
    .asCoroutineDispatcher()
val scope = CoroutineScope(SupervisorJob() + mydisspatcher)


@Service
class RainForecastServiceImpl(
    private val httpClient: HttpClient
) : RainForecastService {
    companion object: KLogging()

    private val bot: TelegramBot = TelegramBot(System.getenv("TGTOKEN"))

    override fun callback(update: Update) {
        scope.launch {
            try {
                val text = update.message!!.text
                if (text != null && Commands.any(text)) {
                    requestLocation(update.message!!.chat!!.id)
                    return@launch
                }

                executeCallback(
                    update.message!!.location,
                    update.message!!.text,
                    update.message!!.chat!!.id
                )
            } catch (e: Exception) {
                logger.error { "Error: ${e}, update: ${update}" }
                sendMessageToUser(update.message!!.chat!!.id, "Ошибка получения информации о погоде")
            }
        }
    }

    private suspend fun executeCallback(location: Location?, message: String?, chatId: Int) {
        val apiWeather = buildApiWeather(location)
        val responseMessage = apiWeather.load(location, message, httpClient)
        sendMessageToUser(chatId, responseMessage)
    }

    private fun buildApiWeather(location: Location?): LoadWeather {
        return if (location == null) LoadWeatherCurrentImpl() else LoadWeatherOneCallImpl()
    }

    private fun sendMessageToUser(chatId: Int, message: String) {
        bot.execute(
            SendMessage(chatId, message)
                .replyMarkup(
                    ReplyKeyboardMarkup(
                        KeyboardButton("")
                            .requestLocation(true)
                    )
                )
        )
    }

    private fun requestLocation(chatId: Int) {
        bot.execute(
            SendMessage(chatId, "Для определения погоды необходимо отправить текущую локацию")
                .replyMarkup(
                    ReplyKeyboardMarkup(
                        KeyboardButton("Нажмите для отправки локации")
                            .requestLocation(true)
                    )
                )
        )
    }

    override fun register(): String {
        val request: SetWebhook = SetWebhook()
            .url(System.getenv("WEBHOOK_URL") + System.getenv("TGTOKEN"))
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
