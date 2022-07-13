package br.com.portfolio.multitenant.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChan(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
            .anyRequest().authenticated()
            .and()
                .httpBasic()
            .and()
                .formLogin()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
            .headers().frameOptions().disable()
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            web -> web.ignoring()
                .antMatchers("/images/**", "/js/**", "/webjars/**")
        }
    }
}