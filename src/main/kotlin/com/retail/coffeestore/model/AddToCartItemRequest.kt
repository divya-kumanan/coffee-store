package com.retail.coffeestore.model

data class AddToCartItemRequest(
    val toppingIds: List<Long>,
    val quantity: Int
)