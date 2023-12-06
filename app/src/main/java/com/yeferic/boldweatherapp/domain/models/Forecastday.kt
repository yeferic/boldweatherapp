package com.yeferic.boldweatherapp.domain.models

import com.google.gson.annotations.SerializedName
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING

data class Forecastday(
    @SerializedName("date") var date: String = EMPTY_STRING,
    @SerializedName("day") var day: Day = Day(),
)
