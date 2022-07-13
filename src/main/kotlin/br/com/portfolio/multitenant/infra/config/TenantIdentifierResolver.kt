package br.com.portfolio.multitenant.infra.config

import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Predicate

@Component
class TenantIdentifierResolver : CurrentTenantIdentifierResolver {
    override fun resolveCurrentTenantIdentifier(): String {
        return Optional.ofNullable(SecurityContextHolder.getContext().authentication)
            .filter(Predicate.not { authentication: Authentication? -> authentication is AnonymousAuthenticationToken })
            .map { obj: Authentication -> obj.name }
            .orElse(DEFAULT_TENANT)
    }

    override fun validateExistingCurrentSessions(): Boolean {
        return false
    }

    companion object {
        const val DEFAULT_TENANT = "default"
    }
}
