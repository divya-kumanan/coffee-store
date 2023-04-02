package com.retail.coffeestore.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "drinks")
data class Drink(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String = "",

    var price: Double = 0.0,

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "drink_toppings",
        joinColumns = [JoinColumn(name = "drink_id")],
        inverseJoinColumns = [JoinColumn(name = "topping_id")]
    )
    var toppings: List<Topping> = emptyList()
)