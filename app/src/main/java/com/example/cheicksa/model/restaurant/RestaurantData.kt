package com.example.cheicksa.model.restaurant

import com.google.firebase.Timestamp
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class RestaurantData(
    val id: Long = 0L,
    val image: Int = 0,
    val imageUrl: String = "",
    val name: String = "",
    val category: String ="",
    val minOrder: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val description: String = "",
    val mealsList: List<MealsList> = emptyList(),
    val deliveryTime: Double = 10.0,
    val rate: Double = 3.9,
    val raterCount: Long = 5000,
    val isSuperRestaurant: Boolean = false,
    val foodEmoji: String = "",
    val openingTime: String = "",
    val closingTime: String = "",
    val openingDays: List<String> = emptyList(),
    val isClosed: Boolean = false,
    val restaurantAllowed: Boolean = false,
    val registrationDate: String = "",
    val address: Address = Address(),
    val isVerified: Boolean = false,
    val isBlocked: Boolean = false,
){
    constructor(): this(
        id = 0L,
        image = 0,
        imageUrl = "",
        name = "",
        category = "",
        minOrder = 0.0,
        deliveryFee = 0.0,
        description = "",
        mealsList = emptyList(),
        deliveryTime = 10.0,
        rate = 3.9,
        raterCount = 5000,
        isSuperRestaurant = false,
        foodEmoji = "",
        openingTime = "",
        closingTime = "",
        openingDays = emptyList(),
        isClosed = false,
        restaurantAllowed = false,
        registrationDate = "",
        address = Address(),
        isVerified = false,
        isBlocked = false,
    )
}

@Serializable
data class Extra(
    val title: String,
    val name: String,
    val price: String,
    val imageUrl: String = "",
    val allowed : Boolean = false,
    val quantity: Int = 0,
){
    constructor(): this(
        title = "",
        name = "",
        price = "",
        imageUrl = "",
        allowed = false,
        quantity = 0,
    )
}

