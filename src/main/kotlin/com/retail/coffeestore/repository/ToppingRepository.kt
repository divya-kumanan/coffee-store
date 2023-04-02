package com.retail.coffeestore.repository

import com.retail.coffeestore.model.Topping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ToppingRepository : JpaRepository<Topping, Long>