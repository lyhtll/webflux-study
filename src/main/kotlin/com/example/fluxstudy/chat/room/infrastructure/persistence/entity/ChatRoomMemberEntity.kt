package com.example.fluxstudy.chat.room.infrastructure.persistence.entity

import com.example.fluxstudy.chat.room.domain.model.ChatRoomMember
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "chat_room_members")
data class ChatRoomMemberEntity(
    @Id
    val id: UUID? = null,
    val roomId: UUID,
    val userId: UUID,
    val joinedAt: LocalDateTime,
)

fun ChatRoomMemberEntity.toDomain(): ChatRoomMember =
    ChatRoomMember(
        roomId = roomId,
        userId = userId,
        joinedAt = joinedAt,
    )

fun ChatRoomMember.toEntity(): ChatRoomMemberEntity =
    ChatRoomMemberEntity(
        roomId = roomId,
        userId = userId,
        joinedAt = joinedAt,
    )