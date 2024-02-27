package io.albrains.moviebrains.identity.main.common.controller

import org.springframework.http.HttpStatus

data class ErrorDto(var message: String, val httpStatus: HttpStatus)
