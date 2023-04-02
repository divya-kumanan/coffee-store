package com.retail.coffeestore.service

import com.retail.coffeestore.exception.NotFoundException
import com.retail.coffeestore.model.Topping
import com.retail.coffeestore.repository.ToppingRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ToppingService(private val toppingRepository: ToppingRepository) {

    @PostConstruct
    fun addToppingsOnStartup() {
        val toppings = listOf(
            Topping(name = "Milk", price = 2.0),
            Topping(name = "Hazelnut syrup", price = 3.0),
            Topping(name = "Chocolate sauce", price = 5.0),
            Topping(name = "Lemon", price = 2.0)
        )
        toppingRepository.saveAll(toppings)
    }

    fun getAllToppings(): List<Topping> = toppingRepository.findAll()

    fun getToppingById(id: Long): Topping = toppingRepository.findById(id).orElseThrow { NotFoundException("Topping not found with ID: $id") }

    fun addTopping(topping: Topping): Topping = toppingRepository.save(topping)

    fun updateTopping(id: Long, updatedTopping: Topping): Topping {
        val existingTopping = getToppingById(id)
        val updated = existingTopping.copy(name = updatedTopping.name, price = updatedTopping.price)
        return toppingRepository.save(updated)
    }

    fun deleteTopping(id: Long) {
        val topping = getToppingById(id)
        return toppingRepository.delete(topping)
    }

}