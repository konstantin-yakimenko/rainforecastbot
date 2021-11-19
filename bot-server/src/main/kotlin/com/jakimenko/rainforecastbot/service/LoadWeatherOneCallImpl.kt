package com.jakimenko.rainforecastbot.service

import com.jakimenko.rainforecastbot.dto.openweathermap.OnecallResponse
import com.jakimenko.rainforecastbot.dto.telegram.Location
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class LoadWeatherOneCallImpl(
    private val api: WeatherApiCall<OnecallResponse> = WeatherApiCallImpl(),
    private val openweathermapAppId: String = System.getenv("OPENWEATHERMAP_APP_ID")
): LoadWeather {

    override suspend fun load(location: Location?, text: String?, httpClient: HttpClient): String {
        val weather = api.callWeatherApi(buildUrl(location!!), OnecallResponse::class.java, httpClient)
        return buildResponseMessage(weather)
    }

    private fun buildUrl(location: Location): String {
        return API_URL + "onecall?lat=${location.latitude}&lon=${location.longitude}&exclude=daily,alerts&units=metric&lang=ru&appid=$openweathermapAppId"
    }

    private fun buildResponseMessage(weather: OnecallResponse): String {
        var message = weather.current.weather.first().description+"\n"
        message += "температура ${weather.current.temp}\n"
        message += "ощущается как ${weather.current.feels_like}\n"
        if (weather.current.clouds != null) {
            message += "облачность ${weather.current.clouds} %\n"
        }
        message += "ветер ${weather.current.wind_speed} м/с\n"
        if (weather.current.rain != null) {
            message += "дождь ${weather.current.rain.hour} мм\n"
        }
        if (weather.current.snow != null) {
            message += "снег ${weather.current.snow.hour} мм\n"
        }
        message += "восход ${calcDate(weather.current.sunrise, weather.timezone)}\n"
        message += "закат  ${calcDate(weather.current.sunset, weather.timezone)}\n"

        var predictionPrecipitation = 0L
        var predictionRain = 0L
        var predictionSnow = 0L
        val now = Instant.now().atZone(ZoneId.of(weather.timezone))

        weather.minutely
            ?.forEach {
                if (it.precipitation > 0 && predictionPrecipitation == 0L) {
                    val minute = fromInstant(it.dt, weather.timezone)
                    predictionPrecipitation = ChronoUnit.MINUTES.between(now, minute)
                }
            }
        weather.hourly
            ?.forEach {
                val hour = fromInstant(it.dt, weather.timezone)
                val hoursDifferent = ChronoUnit.HOURS.between(now, hour)
                if (hoursDifferent > 24) {
                    return@forEach
                }
                if (it.rain != null && it.rain.hour > 0 && predictionRain == 0L) {
                    predictionRain = hoursDifferent
                }
                if (it.snow != null && it.snow.hour > 0 && predictionSnow == 0L) {
                    predictionSnow = hoursDifferent
                }
            }

        if (weather.current.rain == null && weather.current.snow == null) {
            if (predictionPrecipitation > 0) {
                message += "Ожидаются осадки через ${predictionPrecipitation} мин"
            } else if (predictionRain > 0) {
                message += "Ожидается дождь через ${predictionRain} ч"
            } else if (predictionSnow > 0) {
                message += "Ожидается снег через ${predictionSnow} ч"
            } else {
                message += "Осадков не ожидается"
            }
        }
        return message
    }

    private fun calcDate(time: Int, timezone: String): String {
        return java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME
            .format(Instant.ofEpochSecond(time.toLong())
                .atZone(ZoneId.of(timezone))
                .withZoneSameInstant(ZoneId.of(timezone))
            )
            .substring(0, 19)
            .replace("T", " ")
    }

    private fun fromInstant(time: Int, timezone: String): ZonedDateTime {
        return Instant
            .ofEpochSecond(time.toLong())
            .atZone(ZoneId.of(timezone))
    }
}
