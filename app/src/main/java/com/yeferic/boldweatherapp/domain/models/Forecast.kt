package com.yeferic.boldweatherapp.domain.models

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday") var forecastday: List<Forecastday> = arrayListOf(),
)
