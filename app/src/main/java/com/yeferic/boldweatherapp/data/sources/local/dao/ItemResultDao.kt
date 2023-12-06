package com.yeferic.boldweatherapp.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.yeferic.boldweatherapp.domain.models.ItemResult

@Dao
interface ItemResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemResult): Long

    @Query("DELETE FROM itemresult")
    suspend fun deleteItems(): Int

    @Query("SELECT * from itemresult")
    suspend fun getItems(): List<ItemResult>

    @Transaction
    suspend fun saveItems(items: List<ItemResult>) {
        deleteItems()
        items.forEach {
            insert(it)
        }
    }
}
