package com.example.cheicksa.model.gpt.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    //@SerialName("location")
   // val location: Location,
    //@SerialName("unit")
    //val unit: Unit
    val restaurantName: RestaurantName,
    val foodName: FoodName,
    val foodCategory: FoodCategory,
    val foodQuantity: FoodQuantity,
    val foodPrice: FoodPrice
)

@Serializable
data class RestaurantName(
    val type: String = "String",
    val description: String
)
@Serializable
data class FoodName(
    val type: String = "String",
    val description: String
)
@Serializable
data class FoodCategory(
    val type: String = "String",
    val description: String
)
@Serializable
data class FoodQuantity(
    val type: String = "String",
    val description: String
)
@Serializable
data class FoodPrice(
    val type: String = "String",
    val `enum`: List<String>
)
