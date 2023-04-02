package com.retail.coffeestore.controller

import com.retail.coffeestore.model.ErrorResponse
import com.retail.coffeestore.model.Topping
import com.retail.coffeestore.service.ToppingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/toppings")
class ToppingController(val toppingService: ToppingService) {
    @get:GetMapping
    @get:Operation(summary = "Retrieve Topping")
    @get:ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Retrieved Topping Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Topping::class))
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
    val toppings: List<Topping>
        get() = toppingService.getAllToppings()

    @PostMapping
    @Operation(summary = "Add Topping")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Topping Added Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Topping::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Topping Request",
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
    fun addTopping(@RequestBody topping: Topping): Topping {
        return toppingService.addTopping(topping)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve Topping by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Retrieved Topping Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Topping::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Topping Request",
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
    fun getToppingById(@PathVariable id: Long?): Topping? {
        return toppingService.getToppingById(id!!)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Topping by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Updated Topping Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Topping::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Topping Request",
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
    fun updateTopping(@RequestBody topping: Topping, @PathVariable id: Long): Topping? {
        return toppingService.updateTopping(id, topping)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Topping by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204", description = "Deleted Topping Successfully"
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Topping Request",
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
    fun deleteTopping(@PathVariable id: Long?) {
        toppingService.deleteTopping(id!!)
    }
}