package com.example.cheicksa.model.gpt.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    @SerialName("location")
    val location: Location,
    @SerialName("unit")
    val unit: Unit
)