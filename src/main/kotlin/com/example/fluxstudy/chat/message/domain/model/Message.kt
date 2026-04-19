package com.example.fluxstudy.chat.message.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Message(
    val id: String? = null,
    val roomId: UUID,
    val senderId: UUID,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
