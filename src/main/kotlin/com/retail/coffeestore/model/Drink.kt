package com.retail.coffeestore.model
import javax.persistence.*

@Entity
@Table(name = "drinks")
data class Drink(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String = "",

    var price: Double = 0.0
)
