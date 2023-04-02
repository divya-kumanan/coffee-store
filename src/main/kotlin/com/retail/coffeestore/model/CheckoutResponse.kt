package com.retail.coffeestore.model

data class Checkout(
    val id: Long,
    val drink: DrinkResponse,
    val toppings: List<ToppingResponse>,
    val quantity: Int,
    val originalAmount: Double
)

data class CheckoutResponse(
    val items: List<Checkout?>,
    val originalAmount: Double,
    val discountAmount: Double,
    val discountedAmount: Double
)