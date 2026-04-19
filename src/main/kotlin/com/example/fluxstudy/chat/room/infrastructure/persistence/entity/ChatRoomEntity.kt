package com.example.fluxstudy.chat.room.infrastructure.persistence.entity

import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("chat_rooms")
data class ChatRoomEntity(
    @Id
    val id: UUID,
    val type: ChatRoomType,
    val ownerId: UUID,
    val name: String? = null,
    val firstUserId: UUID? = null,
    val secondUserId: UUID? = null,
    val createdAt: LocalDateTime,
)

fun ChatRoomEntity.toDomain(): ChatRoom = when (type) {
    ChatRoomType.DIRECT -> ChatRoom.Direct(
        id = id,
        ownerId = ownerId,
        createdAt = createdAt,
        firstUserId = firstUserId!!,
        secondUserId = secondUserId!!,
    )
    ChatRoomType.GROUP -> ChatRoom.Group(
        id = id,
        ownerId = ownerId,
        createdAt = createdAt,
        name = name!!,
    )
}

fun ChatRoom.toEntity(): ChatRoomEntity = when (this) {
    is ChatRoom.Direct -> ChatRoomEntity(
        id = id,
        type = ChatRoomType.DIRECT,
        ownerId = ownerId,
        createdAt = createdAt,
        firstUserId = firstUserId,
        secondUserId = secondUserId,
    )
    is ChatRoom.Group -> ChatRoomEntity(
        id = id,
        type = ChatRoomType.GROUP,
        ownerId = ownerId,
        createdAt = createdAt,
        name = name,
    )
}