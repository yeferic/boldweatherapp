package com.yeferic.boldweatherapp.domain.models

import com.google.gson.annotations.SerializedName
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING

data class Condition(
    @SerializedName("text") var text: String = EMPTY_STRING,
    @SerializedName("icon") var icon: String = EMPTY_STRING,
){
    fun getUrlIcon(): String = "https:$icon"
}
