package com.example.fluxstudy.user.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class User(
    val id : UUID = UUID.randomUUID(),
    val name: String,
    val password: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)