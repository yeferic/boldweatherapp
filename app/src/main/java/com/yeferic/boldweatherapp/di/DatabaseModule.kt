package com.yeferic.boldweatherapp.di

import android.app.Application
import androidx.room.Room
import com.yeferic.boldweatherapp.data.sources.local.AppDatabase
import com.yeferic.boldweatherapp.data.sources.local.dao.ItemResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideItemResultDao(appDataBase: AppDatabase): ItemResultDao {
        return appDataBase.itemResultDao()
    }
}
