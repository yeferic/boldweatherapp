package com.yeferic.boldweatherapp.data.sources.remote.dto

import com.google.gson.annotations.SerializedName
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
)

fun ItemResultDto.mapToDomain(): ItemResult {
    return ItemResult(
        id = id,
        name = name,
        region = region,
        country = country,
        url = url,
    )
}
