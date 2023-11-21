package com.example.cheicksa.model

import kotlinx.serialization.Serializable

@Serializable
data class Stores(
    val id: Long = 0L,
    val title: String = ""  ,
    val description: String = "",
    val image: String=""
)
