package com.retail.coffeestore.service

import com.retail.coffeestore.model.CartItem
import com.retail.coffeestore.model.Drink
import com.retail.coffeestore.model.Topping
import com.retail.coffeestore.repository.CartItemRepository
import com.retail.coffeestore.repository.DrinkRepository
import com.retail.coffeestore.repository.ToppingRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockitoExtension::class)
class CartServiceTest {

    @Mock
    lateinit var cartItemRepository: CartItemRepository

    @Mock
    lateinit var drinkRepository: DrinkRepository

    @Mock
    lateinit var toppingRepository: ToppingRepository

    @InjectMocks
    lateinit var cartService: CartService

    @Test
    fun addToCart_WhenDrinkExists_ShouldReturnCartItem() {
        val drinkId = 1L
        val toppingsIds = listOf(1L, 2L)
        val quantity = 2
        val drink = Drink(id = drinkId, name = "Coffee", price = 2.5)
        val toppings = listOf(
            Topping(id = 1L, name = "Milk", price = 0.5),
            Topping(id = 2L, name = "Sugar", price = 0.25)
        )
        `when`(drinkRepository.findById(drinkId)).thenReturn(Optional.of(drink))
        `when`(toppingRepository.findAllById(toppingsIds)).thenReturn(toppings)
        `when`(cartItemRepository.save(any())).thenReturn(CartItem(id=null, drink=Drink(id=1, name="Coffee", price=2.5, toppings= emptyList()), toppings= listOf(Topping(id=1, name="Milk", price=0.5, drinks= emptyList()), Topping(id=2, name="Sugar", price=0.25, drinks= emptyList())), quantity=2, price=null))

        val result = cartService.addToCart(drinkId, toppingsIds, quantity)

        assertEquals(drink, result.drink)
        assertEquals(toppings, result.toppings)
        assertEquals(quantity, result.quantity)
        verify(cartItemRepository).save(result)
    }

    @Test
    fun testCheckout() {
        // create some test data
        val drink1 = Drink(1, "Coffee", 2.5)
        val drink2 = Drink(2, "Tea", 2.0)
        val topping1 = Topping(1, "Milk", 0.5)
        val topping2 = Topping(2, "Sugar", 0.3)
        val cartItem1 = CartItem(1, drink1, listOf(topping1), 2)
        val cartItem2 = CartItem(2, drink2, listOf(topping2), 1)

        // mock cartItemRepository.findAll() to return the test data
        `when`(cartItemRepository.findAll()).thenReturn(listOf(cartItem1, cartItem2))

        // call the method under test
        val result = cartService.checkout()

        // verify that the repository methods were called
        verify(cartItemRepository).findAll()
        verify(cartItemRepository).deleteAll(listOf(cartItem1, cartItem2))

        // verify the expected result
        assertEquals(2, result.items.size)
        assertEquals(8.3, result.originalAmount)
        assertEquals(0.0, result.discountAmount)
        assertEquals(8.3, result.totalAmount)
    }
}