package com.example.fluxstudy.chat.websocket

import java.time.LocalDateTime
import java.util.UUID

data class ChatMessage(
    val messageId: String,
    val senderId: UUID,
    val content: String,
    val createdAt: LocalDateTime,
)
