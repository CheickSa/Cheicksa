package com.example.cheicksa.model.gpt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cheicksa.model.restaurant.OrderScreenCardData
import com.example.cheicksa.model.restaurant.RestaurantData
import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val id: Long = 0L,
    val message: String = "",
    val role: String = "",
    val restaurantData: List<RestaurantData>? = null,
    val meals: List<OrderScreenCardData>? = null,
    val time: String = "",
)
