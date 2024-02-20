package io.albrains.moviebrains.identity.main.user.controller

import io.albrains.moviebrains.identity.main.user.service.domain.UserRegistration
import io.albrains.moviebrains.identity.main.user.service.UserRegistrationService
import io.albrains.moviebrains.identity.main.user.service.domain.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/users")
class UserController(private val userRegistrationService: UserRegistrationService) {

    @GetMapping("/me")
    fun getConnectedUser(principal: Principal): UserResponse {
        return userRegistrationService.getUserById(principal.name)
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: String): UserResponse {
        TODO("update user by id")
    }
}