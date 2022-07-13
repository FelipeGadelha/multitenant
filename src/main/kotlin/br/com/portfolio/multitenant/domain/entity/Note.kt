package br.com.portfolio.multitenant.domain.entity

import javax.persistence.*

@Entity
@Table(name = "notes")
data class Note(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val text: String
)
