package com.retail.coffeestore.repository

import com.retail.coffeestore.model.Drink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DrinkRepository : JpaRepository<Drink, Long>
