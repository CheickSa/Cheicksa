package com.example.cheicksa.injection

import android.content.Context
import androidx.room.Room
import com.example.cheicksa.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDB {
    @Provides
    @Singleton
    fun provideRoomDB(
        @ApplicationContext applicationContext: Context
    ): AppDatabase  {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "order-database"
        ).build()
    }
}