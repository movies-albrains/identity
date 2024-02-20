package io.albrains.moviebrains.identity.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val jwtAuthConverter: JwtAuthConverter) {

    companion object {
        private const val ADMIN = "admin"
        private const val GENERAL = "general"
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf{ it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/users/**").hasRole(GENERAL)
                    .requestMatchers(HttpMethod.POST, "/users/**").hasRole(GENERAL)
                    .requestMatchers(HttpMethod.PUT, "/users/**").hasRole(GENERAL)
                    .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole(GENERAL)
                    .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**",
                        "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt {jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthConverter)
                }
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }
}