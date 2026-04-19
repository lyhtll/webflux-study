package com.example.fluxstudy.chat.room.infrastructure.persistence.repository

import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import com.example.fluxstudy.chat.room.domain.repository.ChatRoomRepository
import com.example.fluxstudy.chat.room.infrastructure.persistence.entity.toDomain
import com.example.fluxstudy.chat.room.infrastructure.persistence.entity.toEntity
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
class ChatRoomRepositoryImpl(
    private val template: R2dbcEntityTemplate,
    private val dao: ChatRoomR2dbcDao,
) : ChatRoomRepository {
    override fun findById(id: UUID): Mono<ChatRoom>  =
        dao.findById(id).map { it.toDomain() }

    override fun findByFirstUserIdAndSecondUserId(
        firstUserId: UUID,
        secondUserId: UUID
    ): Mono<ChatRoom> =
        dao.findByFirstUserIdAndSecondUserId(firstUserId, secondUserId)
            .map { it.toDomain() }

    override fun findAllByUserId(userId: UUID): Flux<ChatRoom> =
        dao.findAllByUserId(userId)
            .map { it.toDomain() }

    override fun create(chatRoom: ChatRoom): Mono<ChatRoom> =
        template.insert(chatRoom.toEntity())
            .map { it.toDomain() }

}