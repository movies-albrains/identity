package io.albrains.moviebrains.identity.main.user.controller

import io.albrains.moviebrains.identity.main.user.service.UserRegistrationService
import io.albrains.moviebrains.identity.main.user.service.domain.UserRegistration
import io.albrains.moviebrains.identity.main.user.service.domain.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/user")
class UserAdminController(private val userRegistrationService: UserRegistrationService) {

    @PostMapping
    fun createUser(@RequestBody userRegistration: UserRegistration): UserResponse {
        return userRegistrationService.createUser(userRegistration)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: String): ResponseEntity<Void> {
        userRegistrationService.deleteUserById(userId)
        return ResponseEntity.noContent().build()
    }
}