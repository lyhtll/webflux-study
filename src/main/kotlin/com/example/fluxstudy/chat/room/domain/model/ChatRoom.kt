package com.example.fluxstudy.chat.room.domain.model

import java.time.LocalDateTime
import java.util.UUID

sealed class ChatRoom {
    abstract val id: UUID
    abstract val ownerId: UUID
    abstract val createdAt: LocalDateTime

    data class Direct(
        override val id: UUID = UUID.randomUUID(),
        override val ownerId: UUID,
        override val createdAt: LocalDateTime = LocalDateTime.now(),
        val firstUserId: UUID,
        val secondUserId: UUID,
    ) : ChatRoom()

    data class Group(
        override val id: UUID = UUID.randomUUID(),
        override val ownerId: UUID,
        override val createdAt: LocalDateTime = LocalDateTime.now(),
        val name: String,
    ) : ChatRoom()
}
