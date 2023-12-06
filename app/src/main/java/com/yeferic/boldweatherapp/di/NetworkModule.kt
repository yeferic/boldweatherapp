package com.yeferic.boldweatherapp.di

import com.yeferic.boldweatherapp.core.commons.Constants
import com.yeferic.boldweatherapp.data.sources.remote.ItemResultApi
import com.yeferic.boldweatherapp.data.sources.remote.exceptions.ErrorHandlerImpl
import com.yeferic.boldweatherapp.domain.exceptions.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_SERVICE)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOKHttpClient(): OkHttpClient {
        val apiKeyInterceptor = Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .addQueryParameter("key", Constants.API_KEY)
                .build()
            val request = chain.request().newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }

        return OkHttpClient()
            .newBuilder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesLoginApi(retrofit: Retrofit): ItemResultApi {
        return retrofit.create(ItemResultApi::class.java)
    }

    @Provides
    @Singleton
    fun errorHandler(): ErrorHandler {
        return ErrorHandlerImpl()
    }
}
