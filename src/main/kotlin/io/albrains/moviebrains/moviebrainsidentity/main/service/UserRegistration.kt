package io.albrains.moviebrains.moviebrainsidentity.main.service

data class UserRegistration(
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val email: String
)
