package br.com.portfolio.multitenant.api.v1.controller

import br.com.portfolio.multitenant.domain.entity.User
import br.com.portfolio.multitenant.domain.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    val userService: UserService
) {
    @GetMapping
    fun findAll() = ResponseEntity.ok(userService.findAll())

    @PostMapping
    fun save(@RequestBody user: User): ResponseEntity<Any> {
        val created = userService.save(user)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(created)
    }
}