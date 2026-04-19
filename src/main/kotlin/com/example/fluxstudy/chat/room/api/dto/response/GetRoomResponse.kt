package com.example.fluxstudy.chat.room.api.dto.response

import java.time.LocalDateTime
import java.util.UUID

data class GetRoomResponse(
    val id: UUID,
    val type: String,
    val name: String?,
    val ownerId: UUID,
    val createdAt: LocalDateTime,
)
