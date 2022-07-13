package br.com.portfolio.multitenant.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(unique = true)
    private val username: String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private val password: String
) : UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return Collections.emptyList()
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean { return true }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean { return true }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean { return true }

    @JsonIgnore
    override fun isEnabled(): Boolean { return true }
}