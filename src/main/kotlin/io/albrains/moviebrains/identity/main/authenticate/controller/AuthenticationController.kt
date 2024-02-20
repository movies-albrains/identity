package io.albrains.moviebrains.identity.main.authenticate.controller

import io.albrains.moviebrains.identity.main.user.service.domain.UserAuthRequest
import io.albrains.moviebrains.identity.main.authenticate.service.UserAuthenticationService
import org.keycloak.representations.AccessTokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authenticate")
class AuthenticationController(private val userAuthenticationService: UserAuthenticationService) {
    @PostMapping
    fun authenticate(@RequestBody userAuthRequest: UserAuthRequest): AccessTokenResponse {
        return userAuthenticationService.authenticate(userAuthRequest)
    }
}
