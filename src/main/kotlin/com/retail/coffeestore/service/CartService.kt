package com.retail.coffeestore.service

import com.retail.coffeestore.exception.NotFoundException
import com.retail.coffeestore.model.*
import com.retail.coffeestore.repository.CartItemRepository
import com.retail.coffeestore.repository.DrinkRepository
import com.retail.coffeestore.repository.ToppingRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.math.max

@Service
class CartService(
    private val cartItemRepository: CartItemRepository,
    private val drinkRepository: DrinkRepository,
    private val toppingRepository: ToppingRepository
) {
    fun addToCart(drinkId: Long, toppingsIds: List<Long>, quantity: Int): CartItem {
        val drink = drinkRepository.findById(drinkId).orElseThrow { NotFoundException("Drink not found") }
        val toppings = toppingRepository.findAllById(toppingsIds).toList()

        val cartItem = CartItem(
            drink = drink,
            toppings = toppings,
            quantity = quantity
        )

        return cartItemRepository.save(cartItem)
    }

    fun getCartItems(): List<CartItem> {
        return cartItemRepository.findAll()
    }

    fun deleteCartItem(id: Long) {
        cartItemRepository.deleteById(id)
    }

    fun checkout(): List<CheckoutResponse> {
        val cartItems = cartItemRepository.findAll()

        val checkoutResponse = cartItems.map { cartItem ->
            cartItem.toCheckoutResponse()
        }

        cartItemRepository.deleteAll(cartItems)
        return checkoutResponse
    }

    private fun CartItem.toCheckoutResponse(): CheckoutResponse {
        val drinkPrice = drink!!.price
        val toppingsPrice = toppings.sumOf { it.price }
        val originalAmount = quantity * (drinkPrice + toppingsPrice)
        val discountAmount = calculateDiscount(originalAmount)
        val discountedAmount = originalAmount - discountAmount

        return CheckoutResponse(
            id = id!!,
            drink = drink!!.toDrinkResponse(),
            toppings = toppings.map { it.toToppingResponse() },
            quantity = quantity,
            originalAmount = originalAmount,
            discountAmount = discountAmount,
            discountedAmount = discountedAmount
        )
    }

    private fun calculateDiscount(amount: Double): Double {
        var discount = 0.0

        // Check for discount 1
        if (amount > 12) {
            discount = amount * 0.25
        }

        // Check for discount 2
        val drinkCount = cartItemRepository.countByDrinkNotNull()
        if (drinkCount >= 3) {
            val cartItems = cartItemRepository.findAllByDrinkNotNullOrderByPriceAsc()
            val freeCartItem = cartItems.firstOrNull()

            if (freeCartItem != null) {
                //apply discount 3
                discount = max(discount, freeCartItem.drink!!.price)
                cartItemRepository.delete(freeCartItem)
            }
        }

        return discount
    }


    fun updateCartItem(id: Long, updateCartItemRequest: UpdateCartItemRequest): CartItem {
        val cartItem = cartItemRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found with id $id")
        }

        if (updateCartItemRequest.quantity <= 0) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity cannot be less than or equal to zero")
        }

        val toppings = updateCartItemRequest.toppingIds.map { toppingId ->
            toppingRepository.findById(toppingId).orElseThrow {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Topping not found with id $toppingId")
            }
        }

        cartItem.quantity = updateCartItemRequest.quantity
        cartItem.toppings = toppings
        return cartItemRepository.save(cartItem)
    }

    fun removeFromCart(id: Long) {
        if (!cartItemRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found with id $id")
        }

        cartItemRepository.deleteById(id)
    }

    fun clearCart() {
        cartItemRepository.deleteAll()
    }
}