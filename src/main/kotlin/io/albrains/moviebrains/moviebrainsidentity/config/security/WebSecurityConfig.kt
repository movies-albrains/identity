package io.albrains.moviebrains.moviebrainsidentity.config.security

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
class WebSecurityConfig(private val logoutHandler: LogoutHandler) {

    companion object {
        private const val GROUPS = "groups"
        private const val REALM_ACCESS_CLAIM = "realm_access"
        private const val ROLES_CLAIM = "roles"
    }
}