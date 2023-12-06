package com.yeferic.boldweatherapp.domain.models

import com.google.gson.annotations.SerializedName

data class ItemDetail(
    @SerializedName("location") var location: Location = Location(),
    @SerializedName("current") var current: Current = Current(),
    @SerializedName("forecast") var forecast: Forecast = Forecast(),
)
