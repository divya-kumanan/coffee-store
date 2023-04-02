package com.retail.coffeestore.model

import javax.persistence.*

@Entity
@Table(name = "toppings")
data class Topping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String = "",

    val price: Double = 0.0
)