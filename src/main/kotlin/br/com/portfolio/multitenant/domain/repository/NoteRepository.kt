package br.com.portfolio.multitenant.domain.repository

import br.com.portfolio.multitenant.domain.entity.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository: JpaRepository<Note, Long>