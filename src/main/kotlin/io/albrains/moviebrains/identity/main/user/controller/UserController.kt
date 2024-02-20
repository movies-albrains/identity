package io.albrains.moviebrains.identity.main.user.controller

import io.albrains.moviebrains.identity.main.user.service.domain.UserRegistration
import io.albrains.moviebrains.identity.main.user.service.UserRegistrationService
import io.albrains.moviebrains.identity.main.user.service.domain.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/users")
class UserController(private val userRegistrationService: UserRegistrationService) {

    @GetMapping("/connected")
    fun getUser(principal: Principal): UserResponse {
        return userRegistrationService.getUserById(principal.name)
    }
}