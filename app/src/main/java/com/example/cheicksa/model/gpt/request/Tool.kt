package com.example.cheicksa.model.gpt.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tool(
    @SerialName("type")
    val type: String,
    @SerialName("function")
    val function: Function
)