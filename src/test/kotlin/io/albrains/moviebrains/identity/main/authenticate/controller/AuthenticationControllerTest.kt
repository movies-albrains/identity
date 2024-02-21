package io.albrains.moviebrains.identity.main.authenticate.controller

import io.albrains.moviebrains.identity.common.KeycloakTestContainers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AuthenticationControllerTest: KeycloakTestContainers() {
    @Test
    fun firstTest() {
        assertThat(true).isTrue()
    }

    @Test
    fun secondTest() {
        assertThat(true).isTrue()
    }
}