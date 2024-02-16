package io.albrains.moviebrains.moviebrainsidentity.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList


@Component
class JwtAuthConverter: Converter<Jwt, AbstractAuthenticationToken> {

    @Value("#{jwt.auth.converter.resource-id}")
    private lateinit var resourceId: String

    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    override fun convert(jwt: Jwt): AbstractAuthenticationToken? {
        val authorities = jwtGrantedAuthoritiesConverter.convert(jwt)
            ?.plus(extractResourceRoles(jwt))
        return JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt))
    }

    private fun getPrincipalClaimName(jwt: Jwt): String {
        val claimName = JwtClaimNames.SUB
        return jwt.getClaim(claimName)
    }

    private fun extractResourceRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val realmAccess: Map<String, Collection<String>>? = jwt.getClaim("realm_access")
        val resourceAccess: Map<String, Map<String, Collection<String>>>? = jwt.getClaim("resource_access")

        var allRoles: Collection<String> = ArrayList()
        val resourceRoles: Collection<String>
        val realmRoles: Collection<String>

        if (resourceAccess != null && resourceAccess["account"] != null) {
            val account = resourceAccess["account"] as Map<String, Collection<String>>
            if (account.containsKey("roles")) {
                resourceRoles = account["roles"] as Collection<String>
                allRoles = allRoles.plus(resourceRoles)
            }
        }

        if(realmAccess != null && realmAccess.containsKey("roles")){
            realmRoles = realmAccess["roles"] as Collection<String>
            allRoles = allRoles.plus(realmRoles)
        }

        if (allRoles.isEmpty() || !Objects.equals(resourceId,jwt.getClaim("azp")) ) {
            return setOf();
        }

        return allRoles.map { SimpleGrantedAuthority("ROLE_$it") }
    }
}