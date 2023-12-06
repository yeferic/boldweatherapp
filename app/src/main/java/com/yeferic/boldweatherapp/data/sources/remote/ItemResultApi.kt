package com.yeferic.boldweatherapp.data.sources.remote

import com.yeferic.boldweatherapp.data.sources.remote.dto.ItemResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemResultApi {
    @GET("v1/search.json")
    suspend fun search(
        @Query("q") query: String,
    ): Response<List<ItemResultDto>>
}
