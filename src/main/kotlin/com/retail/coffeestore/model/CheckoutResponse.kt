package com.retail.coffeestore.model

data class CheckoutResponse(
    val id: Long,
    val drink: DrinkResponse,
    val toppings: List<ToppingResponse>,
    val quantity: Int,
    val originalAmount: Double,
    val discountAmount: Double,
    val discountedAmount: Double
)

data class Checkout(
    val response: List<CheckoutResponse>,
    val totalOriginalAmount: Double,
    val totalDiscountAmount: Double,
    val totalAmount: Double
)