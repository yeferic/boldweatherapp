package com.yeferic.boldweatherapp.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yeferic.boldweatherapp.data.sources.local.dao.ItemResultDao
import com.yeferic.boldweatherapp.domain.models.ItemResult

@Database(
    entities = [
        ItemResult::class,
    ],
    version = AppDatabase.VERSION,
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun itemResultDao(): ItemResultDao

    companion object {
        const val DB_NAME = "boldwatherapp.db"
        const val VERSION = 1
    }
}
