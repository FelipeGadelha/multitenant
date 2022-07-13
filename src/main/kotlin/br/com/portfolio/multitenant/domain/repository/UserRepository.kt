package br.com.portfolio.multitenant.domain.repository

import br.com.portfolio.multitenant.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
}