package com.example.cheicksa.model

data class Bill(
    var initialPrice: Double = 300.0,
    var price: Double = 300.0,
    var amount: Int = 1,
    var deliveryFee: Double = 0.0,
    var tax: Double = 10.0,
    var total: Double = 310.0,
    var discount: Double = 0.0
)