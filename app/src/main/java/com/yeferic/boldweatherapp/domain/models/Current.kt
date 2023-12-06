package com.yeferic.boldweatherapp.domain.models

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c") var tempC: Double = 0.0,
    @SerializedName("condition") var condition: Condition = Condition(),
){
    fun getTemperature(): String = "$tempC °C"
}
