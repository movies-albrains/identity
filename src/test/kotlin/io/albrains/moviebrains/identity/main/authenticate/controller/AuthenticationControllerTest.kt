package io.albrains.moviebrains.identity.main.authenticate.controller

import io.albrains.moviebrains.identity.common.KeycloakTestContainersConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(KeycloakTestContainersConfig::class)
class AuthenticationControllerTest {
    @Test
    fun firstTest() {
        assertThat(true).isTrue()
    }

    @Test
    fun secondTest() {
        assertThat(true).isTrue()
    }
}