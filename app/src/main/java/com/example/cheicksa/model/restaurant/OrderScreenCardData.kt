package com.example.cheicksa.model.restaurant

import android.net.Uri
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class OrderScreenCardData(
    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val title: String,
    val description: String,
    val price: Double,
    //@Contextual val image: Uri? = null,
    val imageUlr: String? = null,
    var extra: List<Extra> = emptyList<Extra>().toMutableList(),
    var allowed: Boolean = false,
    val orderTime: String = "20:44:56.995764",
    val specialInstructions: String = "",
    val otherInfo: String = "",
){
    constructor(): this(
        id = "",
        type = "",
        title = "",
        description = "",
        price = 0.0,
        imageUlr = "",
        extra = emptyList<Extra>().toMutableList(),
        allowed = false,
        orderTime = "",
        specialInstructions = "",
        otherInfo = "",
    )
}



