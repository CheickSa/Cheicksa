package com.example.cheicksa

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cheicksa.model.dao.OrderDao
import com.example.cheicksa.model.restaurant.ExtraTypeConverter
import com.example.cheicksa.model.restaurant.OrderInfo
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application()

@Database(entities = [OrderInfo::class], version = 1)
@TypeConverters(ExtraTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}