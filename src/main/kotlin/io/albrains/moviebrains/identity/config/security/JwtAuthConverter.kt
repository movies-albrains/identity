package io.albrains.moviebrains.identity.config.security

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

// TODO: à mettre dans une librairie
@Component
class JwtAuthConverter: Converter<Jwt, AbstractAuthenticationToken> {

//    @Value("\${jwt.auth.converter.resource-id}")
//    private lateinit var resourceId: String

    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    companion object {
        private const val REALM_ACCESS_CLAIM = "realm_access"
        private const val ROLES_CLAIM = "roles"
        private const val RESOURCE_ACCESS_CLAIM = "resource_access"
    }

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
        val realmAccess: Map<String, Collection<String>>? = jwt.getClaim(REALM_ACCESS_CLAIM)
        val resourceAccess: Map<String, Map<String, Collection<String>>>? = jwt.getClaim(RESOURCE_ACCESS_CLAIM)

        var allRoles: Collection<String> = ArrayList()
        val resourceRoles: Collection<String>
        val realmRoles: Collection<String>

        if (resourceAccess != null && resourceAccess["account"] != null) {
            val account = resourceAccess["account"] as Map<String, Collection<String>>
            if (account.containsKey(ROLES_CLAIM)) {
                resourceRoles = account[ROLES_CLAIM] as Collection<String>
                allRoles = allRoles.plus(resourceRoles)
            }
        }

        if(realmAccess != null && realmAccess.containsKey(ROLES_CLAIM)){
            realmRoles = realmAccess[ROLES_CLAIM] as Collection<String>
            allRoles = allRoles.plus(realmRoles)
        }

        // TODO verify the client
        /*if (allRoles.isEmpty() || !Objects.equals(resourceId,jwt.getClaim("azp")) ) {
            return setOf();
        }*/

        return allRoles.map { SimpleGrantedAuthority("ROLE_$it") }
    }
}