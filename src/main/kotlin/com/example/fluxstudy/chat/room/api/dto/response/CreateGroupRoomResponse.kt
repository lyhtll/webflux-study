package com.example.fluxstudy.chat.room.api.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class CreateGroupRoomResponse(
    val id: UUID,
    val name: String,
    val ownerId: UUID,
    val createdAt: LocalDateTime,
)
