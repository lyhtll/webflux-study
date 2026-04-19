package com.example.fluxstudy.chat.message.infrastructure.persistence.document

import com.example.fluxstudy.chat.message.domain.model.Message
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "messages")
data class MessageDocument(
    @Id val id: String? = null,  // MongoDB ObjectId는 String으로 매핑
    val roomId: UUID,
    val senderId: UUID,
    val content: String,
    val createdAt: LocalDateTime,
)

fun MessageDocument.toDomain() = Message(
    id = id,
    roomId = roomId,
    senderId = senderId,
    content = content,
    createdAt = createdAt,
)

fun Message.toDocument() = MessageDocument(
    id = id,
    roomId = roomId,
    senderId = senderId,
    content = content,
    createdAt = createdAt,
)