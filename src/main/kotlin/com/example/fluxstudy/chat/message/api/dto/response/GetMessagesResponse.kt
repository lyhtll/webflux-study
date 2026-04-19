package com.example.fluxstudy.chat.message.api.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class GetMessagesResponse(
    val id: String,
    val roomId: UUID,
    val senderId: UUID,
    val content: String,
    val createdAt: LocalDateTime,
)
