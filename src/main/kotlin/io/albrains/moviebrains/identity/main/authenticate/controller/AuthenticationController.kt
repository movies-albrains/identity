package io.albrains.moviebrains.identity.main.authenticate.controller

import io.albrains.moviebrains.identity.main.user.service.domain.AuthRequest
import io.albrains.moviebrains.identity.main.authenticate.service.UserAuthenticationService
import org.keycloak.representations.AccessTokenResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/authenticate")
class AuthenticationController(private val userAuthenticationService: UserAuthenticationService) {
    @PostMapping
    fun authenticate(@RequestBody authRequest: AuthRequest): AccessTokenResponse {
        return userAuthenticationService.authenticate(authRequest)
    }

    @GetMapping("/sessions/active")
    fun getActiveSessions(principal: Principal) {
        TODO("get active sessions")
    }
}
