package com.example.cheicksa.model.restaurant

import kotlinx.serialization.Serializable


@Serializable
data class Club(
    val title: String = "Gagner des coupon de reduction",
    val description: String = "Rejoingnez notre CheickSa club",
)
