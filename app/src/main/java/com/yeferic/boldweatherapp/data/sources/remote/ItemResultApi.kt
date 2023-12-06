package com.yeferic.boldweatherapp.data.sources.remote

import com.yeferic.boldweatherapp.data.sources.remote.dto.ItemResultDto
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemResultApi {
    @GET("v1/search.json")
    suspend fun search(
        @Query("q") query: String,
    ): Response<List<ItemResultDto>>

    @GET("v1/forecast_.json")
    suspend fun detail(
        @Query("q") name: String,
        @Query("days") days: Int,
    ): Response<ItemDetail>
}
