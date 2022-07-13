package br.com.portfolio.multitenant.api.v1.controller

import br.com.portfolio.multitenant.domain.entity.Note
import br.com.portfolio.multitenant.domain.entity.User
import br.com.portfolio.multitenant.domain.service.NoteService
import br.com.portfolio.multitenant.domain.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/notes")
class NoteController(
    val noteService: NoteService
) {
    @GetMapping
    fun findAll() = ResponseEntity.ok(noteService.findAll())

    @PostMapping
    fun save(@RequestBody note: Note): ResponseEntity<Any> {
        val created = noteService.save(note)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(created)
    }
}