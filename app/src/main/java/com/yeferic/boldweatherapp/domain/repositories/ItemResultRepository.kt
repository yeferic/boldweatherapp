package com.yeferic.boldweatherapp.domain.repositories

import com.yeferic.boldweatherapp.domain.models.ItemResult

interface ItemResultRepository {
    suspend fun search(query: String): List<ItemResult>
    suspend fun saveItems(items: List<ItemResult>)
    suspend fun getItems(from: Int, to: Int): List<ItemResult>
    suspend fun removeItems()
}
