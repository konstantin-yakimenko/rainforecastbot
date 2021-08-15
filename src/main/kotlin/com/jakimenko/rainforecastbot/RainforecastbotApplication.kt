package com.jakimenko.rainforecastbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RainforecastbotApplication {
	fun main(args: Array<String>) {
		runApplication<RainforecastbotApplication>(*args)
	}
}

//fun main(args: Array<String>) {
//	runApplication<RainforecastbotApplication>(*args)
//}
