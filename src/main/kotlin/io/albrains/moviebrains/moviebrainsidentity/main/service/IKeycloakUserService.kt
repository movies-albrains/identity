package io.albrains.moviebrains.moviebrainsidentity.main.service

import org.keycloak.representations.idm.UserRepresentation

interface IKeycloakUserService {

    fun createUser(userRegistration: UserRegistration): UserRegistration
    fun getUserById(id: String): UserRepresentation
    fun deleteUserById(id: String)
}