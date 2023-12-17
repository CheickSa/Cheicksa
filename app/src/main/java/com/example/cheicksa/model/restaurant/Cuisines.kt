package com.example.cheicksa.model.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class Cuisine(
    val title: String = "",
    val imageUrl: String? = null,
)
