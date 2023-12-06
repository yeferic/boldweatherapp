package com.yeferic.boldweatherapp.di

import com.yeferic.boldweatherapp.data.repositories.ItemResultRepositoryImpl
import com.yeferic.boldweatherapp.data.sources.local.dao.ItemResultDao
import com.yeferic.boldweatherapp.data.sources.remote.ItemResultApi
import com.yeferic.boldweatherapp.domain.repositories.ItemResultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideItemResultRepository(
        itemResultDao: ItemResultDao,
        api: ItemResultApi,
    ): ItemResultRepository {
        return ItemResultRepositoryImpl(itemResultDao, api)
    }
}
