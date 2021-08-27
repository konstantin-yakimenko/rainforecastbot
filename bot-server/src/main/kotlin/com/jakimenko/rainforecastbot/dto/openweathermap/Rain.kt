package com.jakimenko.rainforecastbot.dto.openweathermap

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h") val hour: Float,
    @SerializedName("3h") val treeHours: Float?
)
