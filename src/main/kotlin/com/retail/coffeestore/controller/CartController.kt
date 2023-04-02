package com.retail.coffeestore.controller

import com.retail.coffeestore.model.*
import com.retail.coffeestore.service.CartService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService
) {
    @PostMapping("/{drinkId}/toppings")
    @Operation(summary = "Add item to Cart")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Item Added to Cart Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CartItemResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid CartItemRequest",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            )
        ]
    )
    fun addToCart(
        @PathVariable("drinkId") drinkId: Long,
        @RequestBody addToCartRequest: AddToCartItemRequest
    ): ResponseEntity<CartItemResponse> {
        val cartItem = cartService.addToCart(drinkId, addToCartRequest.toppingIds, addToCartRequest.quantity)
        return ResponseEntity.ok(cartItem.toCartItemResponse())
    }

    @GetMapping
    @Operation(summary = "Retrieve Cart Items")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Retrieved Cart Items Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CheckoutResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            )
        ]
    )
    fun getCart(): ResponseEntity<List<CartItemResponse>> {
        val cartItems = cartService.getCartItems()
        val cartItemResponses = cartItems.map { it.toCartItemResponse() }
        return ResponseEntity.ok(cartItemResponses)
    }

    @PutMapping("/{cartItemId}")
    @Operation(summary = "Update CartItem by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Updated CartItem Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CartItemResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid CartItem Request",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            )
        ]
    )
    fun updateCartItem(
        @PathVariable("cartItemId") cartItemId: Long,
        @RequestBody updateCartItemRequest: UpdateCartItemRequest
    ): ResponseEntity<CartItemResponse> {
        val cartItem = cartService.updateCartItem(cartItemId, updateCartItemRequest)
        return ResponseEntity.ok(cartItem.toCartItemResponse())
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "Delete CartItem by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204", description = "Deleted CartItem Successfully"
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid CartItem Request",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            )
        ]
    )
    fun removeFromCart(@PathVariable("cartItemId") cartItemId: Long): ResponseEntity<Unit> {
        cartService.removeFromCart(cartItemId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout Cart Items")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Items checked out Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = CheckoutResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ErrorResponse::class))
                    )]
            )
        ]
    )
    fun checkout(): ResponseEntity<CheckoutResponse> {
        val checkoutResponse = cartService.checkout()
        return ResponseEntity.ok(checkoutResponse)
    }
}