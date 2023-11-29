package com.example.cheicksa.model

data class RestaurantData(
    val id: Long,
    val image: Int,
    val name: String,
    val category: String,
    val minOrder: Double,
    val deliveryFee: Double,
    val description: String = "",
    val mealsList: List<MealsList>,
    val deliveryTime: Double = 10.0,
    val rate: Double = 3.9,
    val raterCount: Long = 5000,
    val isSuperRestaurant: Boolean = false,
    val foodEmoji: String = ""
)