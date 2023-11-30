package com.example.cheicksa.model.gpt.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Function(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("parameters")
    val parameters: Parameters
)