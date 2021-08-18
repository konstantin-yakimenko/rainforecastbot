package com.jakimenko.rainforecastbot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

//@SpringBootTest
class RainforecastbotApplicationTests {

//	@Test
	fun contextLoads() {
		println("start")
		val arr = IntArray(5)
		arr[1] = 5
		var i = 0
		while (i < arr.size) {
			println("i = ${i}")
			println("arr[i] = ${arr[i]}")
			i = i.inc()
		}
	}

}
