package br.com.portfolio.multitenant.domain.service

import br.com.portfolio.multitenant.domain.entity.Note
import br.com.portfolio.multitenant.domain.repository.NoteRepository
import org.springframework.stereotype.Service

@Service
class NoteService(
    private val noteRepository: NoteRepository
) {
    fun save(note: Note) = noteRepository.save(note)
    fun findAll() = noteRepository.findAll()
}