package com.example.fluxstudy.chat.message.infrastructure.persistence.repository

import com.example.fluxstudy.chat.message.domain.model.Message
import com.example.fluxstudy.chat.message.domain.repository.MessageRepository
import com.example.fluxstudy.chat.message.infrastructure.persistence.document.toDocument
import com.example.fluxstudy.chat.message.infrastructure.persistence.document.toDomain
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

class MessageRepositoryImpl(
    private val dao: MessageMongoDao
) : MessageRepository {
    override fun save(message: Message): Mono<Message> =
        dao.save(message.toDocument())
            .map { it.toDomain() }

    override fun findAllByRoomId(roomId: UUID, lastMessageId: String?, limit: Int): Flux<Message> {
        val pageable = PageRequest.of(0, limit)
        return if (lastMessageId == null) {
            dao.findAllByRoomIdOrderByCreatedAtDesc(roomId, pageable)
        } else {
            dao.findByRoomIdAndIdLessThanOrderByCreatedAtDesc(roomId, lastMessageId, pageable)
        }.map { it.toDomain() }
    }

}