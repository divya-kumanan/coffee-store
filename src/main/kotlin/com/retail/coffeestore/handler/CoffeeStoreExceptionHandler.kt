package com.retail.coffeestore.handler

import com.retail.coffeestore.exception.NotFoundException
import com.retail.coffeestore.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.BadRequest

@RestControllerAdvice
class CoffeeStoreExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: Exception): ErrorResponse {
        return ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message)
    }

    @ExceptionHandler(BadRequest::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: Exception): ErrorResponse {
        return ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.message)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception): ErrorResponse {
        return ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message)
    }
}