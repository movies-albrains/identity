package io.albrains.moviebrains.identity.main.user.service

import io.albrains.moviebrains.identity.main.user.service.domain.UserRegistration
import io.albrains.moviebrains.identity.main.user.service.domain.UserResponse
import org.springframework.stereotype.Service

@Service
class UserRegistrationService(private val userRegistrationProvider: UserRegistrationProvider) {

    fun createUser(userRegistration: UserRegistration): UserResponse {
        return userRegistrationProvider.createUser(userRegistration);
    }

    fun getUserById(id: String): UserResponse {
        return userRegistrationProvider.getUserById(id)
    }

    fun deleteUserById(id: String) {
        userRegistrationProvider.deleteUserById(id)
    }
}