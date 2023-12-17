package com.example.cheicksa.model.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class MealsList(
    val mealTitle: String,
    var cards: List<OrderScreenCardData>,
){
    constructor() : this("", emptyList())
}