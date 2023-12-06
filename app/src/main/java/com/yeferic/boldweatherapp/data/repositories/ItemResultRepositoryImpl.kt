package com.yeferic.boldweatherapp.data.repositories

import com.yeferic.boldweatherapp.core.commons.getRange
import com.yeferic.boldweatherapp.data.sources.local.dao.ItemResultDao
import com.yeferic.boldweatherapp.data.sources.remote.ItemResultApi
import com.yeferic.boldweatherapp.data.sources.remote.dto.mapToDomain
import com.yeferic.boldweatherapp.domain.models.ItemResult
import com.yeferic.boldweatherapp.domain.repositories.ItemResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemResultRepositoryImpl @Inject constructor(
    private val itemResultDao: ItemResultDao,
    private val itemResultApi: ItemResultApi,
) : ItemResultRepository {
    override suspend fun search(query: String): List<ItemResult> {
        return withContext(Dispatchers.IO) {
            val response = itemResultApi.search(query = query)
            response.body()?.map {
                it.mapToDomain()
            } ?: arrayListOf()
        }
    }

    override suspend fun saveItems(items: List<ItemResult>) = itemResultDao.saveItems(items)

    override suspend fun getItems(from: Int, to: Int): List<ItemResult> {
        return itemResultDao.getItems().getRange(from, to)
    }

    override suspend fun removeItems() {
        itemResultDao.deleteItems()
    }
}
