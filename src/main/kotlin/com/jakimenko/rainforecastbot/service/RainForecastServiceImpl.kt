package com.jakimenko.rainforecastbot.service

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.request.SendMessage
import com.pengrad.telegrambot.request.SetWebhook
import org.springframework.stereotype.Service


@Service
class RainForecastServiceImpl(
    val bot: TelegramBot = TelegramBot(System.getenv("TGTOKEN"))
): RainForecastService {

    override fun callback(message: Message) {
        println("message = ${message}")
        println("chatId = ${message.chat().id()}")
        val user = message.from()
        val chatId = message.chat().id()
        val response = bot.execute(SendMessage(chatId, "Hello, ${user.firstName()}!"))
        println("response = ${response}")
    }

    override fun register(): String {
        val request: SetWebhook = SetWebhook()
            .url(System.getenv("WEBHOOK_URL") + "/callback")
        val response = bot.execute(request)
        return if (response.isOk)
            "Webhook was registered successful" else "Error webhook regitering"
    }
}
