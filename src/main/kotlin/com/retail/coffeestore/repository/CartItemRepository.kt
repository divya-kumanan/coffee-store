package com.retail.coffeestore.repository

import com.retail.coffeestore.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long>{
        fun findAllByDrinkNotNullOrderByPriceAsc(): List<CartItem>
        fun countByDrinkNotNull(): Long
}