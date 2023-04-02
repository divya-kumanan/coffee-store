package com.retail.coffeestore.controller

import com.retail.coffeestore.model.*
import com.retail.coffeestore.service.CartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService
) {
    @PostMapping("/{drinkId}/toppings")
    fun addToCart(
        @PathVariable("drinkId") drinkId: Long,
        @RequestBody addToCartRequest: AddToCartItemRequest
    ): ResponseEntity<CartItemResponse> {
        val cartItem = cartService.addToCart(drinkId, addToCartRequest.toppingIds, addToCartRequest.quantity)
        return ResponseEntity.ok(cartItem.toCartItemResponse())
    }

    @GetMapping
    fun getCart(): ResponseEntity<List<CartItemResponse>> {
        val cartItems = cartService.getCartItems()
        val cartItemResponses = cartItems.map { it.toCartItemResponse() }
        return ResponseEntity.ok(cartItemResponses)
    }

    @PutMapping("/{cartItemId}")
    fun updateCartItem(
        @PathVariable("cartItemId") cartItemId: Long,
        @RequestBody updateCartItemRequest: UpdateCartItemRequest
    ): ResponseEntity<CartItemResponse> {
        val cartItem = cartService.updateCartItem(cartItemId, updateCartItemRequest)
        return ResponseEntity.ok(cartItem.toCartItemResponse())
    }

    @DeleteMapping("/{cartItemId}")
    fun removeFromCart(@PathVariable("cartItemId") cartItemId: Long): ResponseEntity<Unit> {
        cartService.removeFromCart(cartItemId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/checkout")
    fun checkout(): ResponseEntity<CheckoutResponse> {
        val checkoutResponse = cartService.checkout()
        return ResponseEntity.ok(checkoutResponse)
    }
}