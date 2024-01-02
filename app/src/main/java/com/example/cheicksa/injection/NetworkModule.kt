package com.example.cheicksa.injection

import com.example.cheicksa.remote.Api
import com.example.cheicksa.remote.GptApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
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
    fun provideOkHttpClient(): OkHttpClient {
        val timeoutInSeconds = 30L
        return OkHttpClient.Builder()
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideGptApi(): GptApi {
        return Retrofit.Builder()
            .baseUrl(GptApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create()
    }

}