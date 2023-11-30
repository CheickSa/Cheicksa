package com.example.cheicksa.injection

import com.example.cheicksa.remote.Api
import com.example.cheicksa.remote.GptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Api {
        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
    @Provides
    @Singleton
    fun provideGptApi(): GptApi {
        return Retrofit.Builder()
            .baseUrl(GptApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

}