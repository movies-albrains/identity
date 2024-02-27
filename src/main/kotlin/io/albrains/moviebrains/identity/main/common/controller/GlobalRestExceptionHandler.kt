package io.albrains.moviebrains.identity.main.common.controller

import jakarta.ws.rs.NotAuthorizedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestExceptionHandler {

    @ExceptionHandler(NotAuthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleNotAuthorized(notAuthorizedException: NotAuthorizedException): ErrorDto {
        return ErrorDto(notAuthorizedException.message!!, HttpStatus.UNAUTHORIZED)
    }
}
