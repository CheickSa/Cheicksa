package com.example.cheicksa.model.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val city: String? = null,
    val country: String? = null,
    val line1: String? = null,
    val line2: String? = null,
    val postalCode: String? = null,
    val state: String? = null
){
    constructor() : this(null, null, null, null, null, null)
}
