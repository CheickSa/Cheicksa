package com.example.cheicksa.model

data class Bill (
    var initialPrice: Int = 300,
    var price: Int = 300,
    var amount: Int = 1,
    var deliveryFee: Int = 0,
    var tax: Int = 10,
    var total: Int = 310,
    var discount: Int = 0
)