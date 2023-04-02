package com.retail.coffeestore.controller

import com.retail.coffeestore.model.Topping
import com.retail.coffeestore.service.ToppingService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/toppings")
class ToppingController(val toppingService: ToppingService) {
    @get:GetMapping
    val toppings: List<Topping>
        get() = toppingService.getAllToppings()

    @PostMapping
    fun addTopping(@RequestBody topping: Topping): Topping {
        return toppingService.addTopping(topping)
    }

    @GetMapping("/{id}")
    fun getToppingById(@PathVariable id: Long?): Topping? {
        return toppingService.getToppingById(id!!)
    }

    @PutMapping("/{id}")
    fun updateTopping(@RequestBody topping: Topping, @PathVariable id: Long): Topping? {
        return toppingService.updateTopping(id, topping)
    }

    @DeleteMapping("/{id}")
    fun deleteTopping(@PathVariable id: Long?) {
        toppingService.deleteTopping(id!!)
    }
}