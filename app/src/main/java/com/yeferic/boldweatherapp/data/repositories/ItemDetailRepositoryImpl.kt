package com.yeferic.boldweatherapp.data.repositories

import com.yeferic.boldweatherapp.data.sources.remote.ItemResultApi
import com.yeferic.boldweatherapp.domain.models.ItemDetail
import com.yeferic.boldweatherapp.domain.repositories.ItemDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemDetailRepositoryImpl @Inject constructor(
    private val itemResultApi: ItemResultApi,
) : ItemDetailRepository {
    override suspend fun getDetailItem(name: String, days: Int): ItemDetail {
        return withContext(Dispatchers.IO) {
            val response = itemResultApi.detail(name = name, days = days)
            response.body()!!
        }
    }
}
