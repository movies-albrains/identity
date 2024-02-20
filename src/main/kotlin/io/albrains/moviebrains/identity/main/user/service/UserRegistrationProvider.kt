package io.albrains.moviebrains.identity.main.user.service

import io.albrains.moviebrains.identity.main.user.service.domain.UserRegistration
import io.albrains.moviebrains.identity.main.user.service.domain.UserResponse

interface UserRegistrationProvider {

    fun createUser(userRegistration: UserRegistration): UserResponse
    fun getUserById(id: String): UserResponse
    fun deleteUserById(id: String)
    fun resetPassword(id: String, newPassword: String)
}