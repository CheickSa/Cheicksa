package com.example.cheicksa.model.restaurant

import java.util.UUID

data class Comments(
    val id: String = UUID.randomUUID().toString(),
    val comment: String = "Good Food",
    val date: String = "12/02/2023",
    val name: String = "Moussa",
    val rating: Double = 0.0,
    val restaurantId: String = "",
    val mealId: String = "",
    val userId: String = "",
    val subComments: List<Comments> = emptyList(),
)

data class Likes(
    val mealId: String = "",
    val likes: Long = 0L,
    val dislikes: Long = 0L,
)
