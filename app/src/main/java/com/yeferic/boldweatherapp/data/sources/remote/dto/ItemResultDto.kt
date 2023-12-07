package com.yeferic.boldweatherapp.data.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.yeferic.boldweatherapp.core.commons.Constants.EMPTY_STRING
import com.yeferic.boldweatherapp.domain.models.ItemResult

data class ItemResultDto(
    @SerializedName("id")
    var id: Long,
    @SerializedName("name")
    var name: String,
    @SerializedName("region")
    var region: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("url")
    var url: String,
) {
    companion object {
        fun emptyObject() = ItemResultDto(
            id = 0L,
            name = EMPTY_STRING,
            region = EMPTY_STRING,
            country = EMPTY_STRING,
            url = EMPTY_STRING,
        )
    }
}

fun ItemResultDto.mapToDomain(): ItemResult {
    return ItemResult(
        id = id,
        name = name,
        region = region,
        country = country,
        url = url,
    )
}
