package io.albrains.moviebrains.identity.main.authenticate.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.albrains.moviebrains.identity.common.KeycloakTestContainersConfig
import io.albrains.moviebrains.identity.main.user.service.domain.AuthRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(KeycloakTestContainersConfig::class)
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    var objectMapper: ObjectMapper = Jackson2ObjectMapperBuilder.json().build()

    @Test
    fun shouldAuthenticateWithValidUsernameAndPassword() {
        mockMvc.post(
            "/authenticate"
        ) {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(AuthRequest("alexlionnel", "alex123"))
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun shouldAuthenticateWithInvalidUsernameOrPassword() {
        mockMvc.post(
            "/authenticate"
        ) {
            accept = MediaType.APPLICATION_JSON
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(AuthRequest("alexlionel", "ale123"))
        }.andExpect {
            status { isUnauthorized() }
        }
    }
}