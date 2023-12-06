package com.yeferic.boldweatherapp.domain.repositories

import com.yeferic.boldweatherapp.domain.models.ItemDetail

interface ItemDetailRepository {
    suspend fun getDetailItem(name: String, days: Int): ItemDetail
}
