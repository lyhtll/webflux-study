package com.example.fluxstudy.chat.message.infrastructure.persistence.repository

import com.example.fluxstudy.chat.message.infrastructure.persistence.document.MessageDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import java.util.UUID

interface MessageMongoDao : ReactiveMongoRepository<MessageDocument, String> {
    @Query("{ 'roomId': ?0, '_id': { '\$lt': ?1 } }")
    fun findByRoomIdAndIdLessThanOrderByCreatedAtDesc(
        roomId: UUID,
        lastMessageId: String,
        pageable: Pageable,
    ): Flux<MessageDocument>

    fun findAllByRoomIdOrderByCreatedAtDesc(
        roomId: UUID,
        pageable: Pageable,
    ): Flux<MessageDocument>
}