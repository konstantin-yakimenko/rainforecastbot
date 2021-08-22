package com.jakimenko.rainforecastbot.openweathermap.dto

import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("1h") val hour: Int,
    @SerializedName("3h") val treeHours: Int
)
