package com.example.fluxstudy.chat.room.api.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class CreateDirectRoomResponse(
    val id: UUID,
    val firstUserId: UUID,
    val secondUserId: UUID,
    val createdAt: LocalDateTime,
)
