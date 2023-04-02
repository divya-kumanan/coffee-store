package com.retail.coffeestore.service

import com.retail.coffeestore.exception.NotFoundException
import com.retail.coffeestore.model.Drink
import com.retail.coffeestore.repository.DrinkRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class DrinkService(private val drinkRepository: DrinkRepository) {

    @PostConstruct
    fun addDrinksOnStartup() {
        val drinks = listOf(
            Drink(name = "Black Coffee", price = 4.0),
            Drink(name = "Latte", price = 5.0),
            Drink(name = "Mocha", price = 6.0),
            Drink(name = "Tea", price = 3.0)
        )
        drinkRepository.saveAll(drinks)
    }

    fun getAllDrinks(): List<Drink> {
        return drinkRepository.findAll()
    }

    fun getDrinkById(id: Long): Drink {
        return drinkRepository.findById(id).orElseThrow { NotFoundException("Drink not found with ID: $id") }
    }

    fun addDrink(drink: Drink): Drink {
        return drinkRepository.save(drink)
    }

    fun updateDrink(id: Long, updatedDrink: Drink): Drink {
        val existingDrink = getDrinkById(id)
        val updated = existingDrink.copy(name = updatedDrink.name, price = updatedDrink.price)
        return drinkRepository.save(updated)
    }

    fun deleteDrink(id: Long) {
        val drink = getDrinkById(id)
        drinkRepository.delete(drink)
    }
}