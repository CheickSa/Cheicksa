package com.example.cheicksa.model.restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

@Entity
data class OrderInfo (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val restaurantId: Long = 0L,
    val mealListId: String = "",
    val mealId: String = "",
    val extras: List<Extra> = listOf(),
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
)

class ExtraTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<Extra>? {
        return value?.let {
            Gson().fromJson(it, object : TypeToken<List<Extra>>() {}.type)
        }
    }

    @TypeConverter
    fun toString(value: List<Extra>?): String? {
        return value?.let {
            Gson().toJson(it)
        }
    }
}


