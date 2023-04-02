package com.retail.coffeestore.model

data class UpdateCartItemRequest(
    val quantity: Int,
    val toppingIds: List<Long>
)