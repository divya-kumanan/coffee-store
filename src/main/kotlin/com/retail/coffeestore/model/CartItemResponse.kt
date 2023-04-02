package com.retail.coffeestore.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CartItemResponse(
    val id: Long,
    val drink: DrinkResponse,
    val toppings: List<ToppingResponse>,
    val quantity: Int,
    val price: Double
)

data class DrinkResponse(
    val id: Long,
    val name: String,
    val price: Double,
    val toppings: List<ToppingResponse>
)

fun Drink.toDrinkResponse(): DrinkResponse {
    return DrinkResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        toppings = this.toppings.map { it.toToppingResponse() }
    )
}
fun Topping.toToppingResponse(): ToppingResponse {
    return ToppingResponse(
        id = this.id,
        name = this.name,
        price = this.price
    )
}

data class ToppingResponse(
    val id: Long,
    val name: String,
    val price: Double
)