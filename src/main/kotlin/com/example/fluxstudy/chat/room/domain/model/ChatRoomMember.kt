package com.example.fluxstudy.chat.room.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class ChatRoomMember(
    val roomId: UUID,
    val userId: UUID,
    val joinedAt: LocalDateTime = LocalDateTime.now(),
)
