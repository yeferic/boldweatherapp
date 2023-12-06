package com.yeferic.boldweatherapp.domain.models

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c") var maxtempC: Double = 0.0,
    @SerializedName("mintemp_c") var mintempC: Double = 0.0,
    @SerializedName("avgtemp_c") var avgtempC: Double = 0.0,
    @SerializedName("condition") var condition: Condition = Condition(),
) {
    fun getTemperatureAvg(): String = "$avgtempC °C"
}
