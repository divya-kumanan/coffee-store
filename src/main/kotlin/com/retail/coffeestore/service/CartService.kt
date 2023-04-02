package com.retail.coffeestore.service

import com.retail.coffeestore.exception.NotFoundException
import com.retail.coffeestore.model.*
import com.retail.coffeestore.repository.CartItemRepository
import com.retail.coffeestore.repository.DrinkRepository
import com.retail.coffeestore.repository.ToppingRepository
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(CartService::class.java)


    fun addToCart(drinkId: Long, toppingsIds: List<Long>, quantity: Int): CartItem {
        val drink = drinkRepository.findById(drinkId).orElseThrow { NotFoundException("Drink not found") }
        val toppings = toppingRepository.findAllById(toppingsIds).toList()

        val cartItem = CartItem(
            drink = drink,
            toppings = toppings,
            quantity = quantity
        )
        logger.info("Item added to the cart successfully. ${cartItem.id}")
        return cartItemRepository.save(cartItem)
    }

    fun getCartItems(): List<CartItem> {
        return cartItemRepository.findAll()
    }

    fun deleteCartItem(id: Long) {
        cartItemRepository.deleteById(id)
    }

    fun checkout(): CheckoutResponse {
        val cartItems = cartItemRepository.findAll()

        val originalAmount = cartItems.sumOf { calculatePrice(it) }
        val discountAmount = calculateDiscount(originalAmount)
        val discountedAmount = originalAmount - discountAmount
        val checkouts = cartItems.map { cartItem ->
            cartItem.toCheckout()
        }
        cartItemRepository.deleteAll(cartItems)
        logger.info("Item checked out from the cart successfully. ${checkouts}")
        return CheckoutResponse(
            items = checkouts,
            originalAmount = originalAmount,
            discountAmount = discountAmount,
            totalAmount = discountedAmount
        )
    }

    private fun calculatePrice(cartItem: CartItem): Double {
        val drinkPrice = cartItem.drink?.price ?: throw RuntimeException("Drink cannot be null")
        val toppingsPrice = cartItem.toppings.sumOf { it.price }
        return (drinkPrice + toppingsPrice) * cartItem.quantity
    }

    private fun calculateDiscount(amount: Double): Double {
        var discount = 0.0

        // Check for discount 1
        if (amount > 12) {
            logger.info("Discount 1 applied.")
            discount = amount * 0.25
        }

        // Check for discount 2
        val drinkCount = cartItemRepository.countByDrinkNotNull()
        if (drinkCount >= 3) {
            val cartItems = cartItemRepository.findAllByDrinkNotNullOrderByPriceAsc()
            val freeCartItem = cartItems.firstOrNull()

            if (freeCartItem != null) {
                logger.info("Discount 2 or 3 applied.")
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