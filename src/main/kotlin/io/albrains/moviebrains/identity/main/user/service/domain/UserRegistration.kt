package io.albrains.moviebrains.identity.main.user.service.domain

data class UserRegistration(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
