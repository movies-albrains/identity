package io.albrains.moviebrains.identity.main.service

import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class KeycloakUserService(private val keycloak: Keycloak): IKeycloakUserService {

    @Value("\${keycloak.realm}")
    private lateinit var keycloakRealm: String

    override fun createUser(userRegistration: UserRegistration): UserRegistration {
        val userRepresentation = UserRepresentation()
        userRepresentation.isEnabled = true
        userRepresentation.username = userRegistration.username
        userRepresentation.email = userRegistration.email
        userRepresentation.isEmailVerified = true
        userRepresentation.firstName = userRegistration.firstname
        userRepresentation.lastName = userRegistration.lastname
        // TODO rôle de l'utilisateur

        val credentialRepresentation = CredentialRepresentation()
        credentialRepresentation.value = userRegistration.password
        credentialRepresentation.isTemporary = false
        credentialRepresentation.type = CredentialRepresentation.PASSWORD

        userRepresentation.credentials = listOf(credentialRepresentation)

        val usersResource = keycloak
            .realm(keycloakRealm)
            .users()
        val response = usersResource.create(userRepresentation)

        if (Objects.equals(response.status, 201)) {
            return userRegistration
        }
        TODO("to implement")
    }

    override fun getUserById(id: String): UserRepresentation {
        return keycloak.realm(keycloakRealm)
            .users()
            .get(id)
            .toRepresentation()
    }

    override fun deleteUserById(id: String) {
        keycloak.realm(keycloakRealm)
            .users()
            .delete(id)
    }
}