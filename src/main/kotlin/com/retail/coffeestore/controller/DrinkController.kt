package com.retail.coffeestore.controller

import com.retail.coffeestore.model.Drink
import com.retail.coffeestore.model.ErrorResponse
import com.retail.coffeestore.service.DrinkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/drinks")
@Tag(name = "Drinks API", description = "Drinks API Documentation")
class DrinkController(val drinkService: DrinkService) {

    @get:GetMapping()
    @get:Operation(summary = "Retrieve Drink")
    @get:ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Retrieved Drink Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Drink::class))
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
    val drinks: List<Drink>
        get() = drinkService.getAllDrinks()

    @Operation(summary = "Add Drink")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Drink Added Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Drink::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Drink Request",
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
    @PostMapping
    fun addDrink(@RequestBody drink: Drink): Drink {
        return drinkService.addDrink(drink)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve Drink by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Retrieved Drink Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Drink::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Drink Request",
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
    fun getDrinkById(@PathVariable id: Long?): Drink {
        return drinkService.getDrinkById(id!!)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Drink by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Updated Drink Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = Drink::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Drink Request",
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
    fun updateDrink(@RequestBody drink: Drink, @PathVariable id: Long): Drink {
        return drinkService.updateDrink(id, drink)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Drink by Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204", description = "Deleted Drink Successfully"
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Drink Request",
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
    fun deleteDrink(@PathVariable id: Long) {
        drinkService.deleteDrink(id)
    }
}