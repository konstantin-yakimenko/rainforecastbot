package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.openweathermap.CurrentWeatherInCity
import com.jakimenko.rainforecastbot.dto.openweathermap.OnecallResponse
import com.jakimenko.rainforecastbot.dto.telegram.Location

class LoadWeatherCurrentImpl(
    private val api: WeatherApiCall<OnecallResponse> = WeatherApiCallImpl(),
    private val openweathermapAppId: String = System.getenv("OPENWEATHERMAP_APP_ID")
): LoadWeather {

    override suspend fun load(location: Location?, text: String?, httpClient: HttpClient): String {
        val currentWeather = api.callWeatherApi(buildUrl(location, text), CurrentWeatherInCity::class.java, httpClient)
        return buildResponseMessage(currentWeather)
    }

    private fun buildUrl(location: Location?, city: String?): String {
        if (location != null) {
            return API_URL + "weather?lat=${location.latitude}&lon=${location.longitude}&units=metric&lang=ru&appid=$openweathermapAppId"
        } else if (city != null) {
            return API_URL + "weather?q=${city}&units=metric&lang=ru&appid=$openweathermapAppId"
        } else {
            throw IllegalArgumentException("Некорректные входные данные")
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
}
