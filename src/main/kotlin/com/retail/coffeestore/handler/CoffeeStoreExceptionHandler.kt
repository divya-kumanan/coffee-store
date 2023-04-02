package com.retail.coffeestore.handler

import com.retail.coffeestore.exception.NotFoundException
import com.retail.coffeestore.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CoffeeStoreExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleOrderNotFoundException(ex: Exception): ErrorResponse {
        return ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message)
    }
}