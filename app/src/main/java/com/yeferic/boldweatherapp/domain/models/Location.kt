package com.yeferic.boldweatherapp.domain.models

import com.google.gson.annotations.SerializedName
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING

data class Location(
    @SerializedName("name") var name: String = EMPTY_STRING,
    @SerializedName("region") var region: String = EMPTY_STRING,
    @SerializedName("country") var country: String = EMPTY_STRING,
    @SerializedName("lat") var lat: Double = 0.0,
    @SerializedName("lon") var lon: Double = 0.0,
    @SerializedName("tz_id") var timeZone: String = EMPTY_STRING,
    @SerializedName("localtime") var localtime: String = EMPTY_STRING,
)
