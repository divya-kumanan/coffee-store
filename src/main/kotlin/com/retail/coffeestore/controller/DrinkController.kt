package com.retail.coffeestore.controller

import com.retail.coffeestore.model.Drink
import com.retail.coffeestore.service.DrinkService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/drinks")
class DrinkController(val drinkService: DrinkService) {
    @get:GetMapping()
    val drinks: List<Drink>
        get() = drinkService.getAllDrinks()

    @PostMapping
    fun addDrink(@RequestBody drink: Drink): Drink {
        return drinkService.addDrink(drink)
    }

    @GetMapping("/{id}")
    fun getDrinkById(@PathVariable id: Long?): Drink {
        return drinkService.getDrinkById(id!!)
    }

    @PutMapping("/{id}")
    fun updateDrink(@RequestBody drink: Drink, @PathVariable id: Long): Drink {
        return drinkService.updateDrink(id, drink)
    }

    @DeleteMapping("/{id}")
    fun deleteDrink(@PathVariable id: Long) {
        drinkService.deleteDrink(id)
    }
}