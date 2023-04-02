package com.retail.coffeestore.model

import javax.persistence.*

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    var drink: Drink? = null,

    @ManyToMany
    @JoinTable(
        name = "cart_item_toppings",
        joinColumns = [JoinColumn(name = "cart_item_id")],
        inverseJoinColumns = [JoinColumn(name = "topping_id")]
    )
    var toppings: List<Topping>,

    var quantity: Int = 1,

    @Column(name = "price")
    var price : Double ? = null
) {
    private fun calculatePrice(): Double {
        val drinkPrice = drink?.price ?: throw RuntimeException("Drink cannot be null")
        val toppingsPrice = toppings.sumOf { it.price }
        return (drinkPrice + toppingsPrice) * quantity
    }

    fun toCartItemResponse(): CartItemResponse {
        val drinkResponse = drink?.toDrinkResponse() ?: throw RuntimeException("Drink cannot be null")
        val toppingsResponse = toppings.map { it.toToppingResponse() }
        val calculatedPrice = calculatePrice()
        price = calculatedPrice
        return CartItemResponse(
            id = id ?: throw RuntimeException("Cart Item ID cannot be null"),
            drink = drinkResponse,
            toppings = toppingsResponse,
            quantity = quantity,
            price = calculatedPrice
        )
    }

    fun toCheckout(): Checkout {
        return Checkout(
            id = id!!,
            drink = drink!!.toDrinkResponse(),
            toppings = toppings.map { it.toToppingResponse() },
            quantity = quantity,
            amount = calculatePrice()
        )
    }
}