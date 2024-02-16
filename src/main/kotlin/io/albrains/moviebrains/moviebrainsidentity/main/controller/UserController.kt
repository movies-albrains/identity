package io.albrains.moviebrains.moviebrainsidentity.main.controller

import io.albrains.moviebrains.moviebrainsidentity.main.service.IKeycloakUserService
import io.albrains.moviebrains.moviebrainsidentity.main.service.UserRegistration
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/users")
class UserController(private val keycloakUserService: IKeycloakUserService) {

    @PostMapping
    fun createUser(@RequestBody userRegistration: UserRegistration): UserRegistration {
        return keycloakUserService.createUser(userRegistration)
    }

    @GetMapping
    fun getUser(principal: Principal): UserRepresentation {
        return keycloakUserService.getUserById(principal.name)
    }

    @DeleteMapping
    fun deleteUser(userId: String): ResponseEntity<Void> {
        keycloakUserService.deleteUserById(userId)
        return ResponseEntity.noContent().build()
    }
}