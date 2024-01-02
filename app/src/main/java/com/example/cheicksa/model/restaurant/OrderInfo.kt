package com.example.cheicksa.model.restaurant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Entity
data class OrderInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val restaurantId: String = "",
    val mealListId: String = "",
    val mealId: String = "",
    val extras: List<Extra> = listOf(),
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val time: String = LocalDateTime.now().toString(),
    val title: String = "",
    val specialRequest: String = "",
    val imageUlr: String? = "",
    @Embedded
    val address: Address? = null,
    val initPrice: Double = 0.0,
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


