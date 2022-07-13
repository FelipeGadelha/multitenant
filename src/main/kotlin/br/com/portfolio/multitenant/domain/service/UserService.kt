package br.com.portfolio.multitenant.domain.service

import br.com.portfolio.multitenant.domain.entity.User
import br.com.portfolio.multitenant.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val tenantService: TenantService,
    private val encoder: PasswordEncoder
): UserDetailsService {
    @Transactional
    fun save(user: User): User {
        val encrypt = encoder.encode(user.password)
        val saved = user.copy(password = encrypt)
        userRepository.save(saved)
        tenantService.initDatabase(user.username)
        return saved
    }
    @Transactional
    fun findAll(): List<User> = userRepository.findAll()

//    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
                .orElseThrow { UsernameNotFoundException("User with the specified username is not found") }
    }
}