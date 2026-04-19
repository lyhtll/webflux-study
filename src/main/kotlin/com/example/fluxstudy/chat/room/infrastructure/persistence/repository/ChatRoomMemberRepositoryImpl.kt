package com.example.fluxstudy.chat.room.infrastructure.persistence.repository

import com.example.fluxstudy.chat.room.domain.model.ChatRoomMember
import com.example.fluxstudy.chat.room.domain.repository.ChatRoomMemberRepository
import com.example.fluxstudy.chat.room.infrastructure.persistence.entity.toDomain
import com.example.fluxstudy.chat.room.infrastructure.persistence.entity.toEntity
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
class ChatRoomMemberRepositoryImpl(
    private val template: R2dbcEntityTemplate,
    private val dao: ChatRoomMemberR2dbcDao,
) : ChatRoomMemberRepository {
    override fun findAllByRoomId(roomId: UUID): Flux<ChatRoomMember> =
        dao.findAllByRoomId(roomId)
            .map { it.toDomain() }

    override fun findByRoomIdAndUserId(
        roomId: UUID,
        userId: UUID
    ): Mono<ChatRoomMember> =
        dao.findByRoomIdAndUserId(roomId, userId)
            .map { it.toDomain() }

    override fun existsByRoomIdAndUserId(
        roomId: UUID,
        userId: UUID
    ): Mono<Boolean> =
        dao.existsByRoomIdAndUserId(roomId, userId)

    override fun save(member: ChatRoomMember): Mono<ChatRoomMember> =
        template.insert(member.toEntity())
            .map { it.toDomain() }

    override fun saveAll(members: List<ChatRoomMember>): Flux<ChatRoomMember> =
        Flux.fromIterable(members)
            .flatMap { template.insert(it.toEntity()).map { e -> e.toDomain() } }

    override fun deleteByRoomIdAndUserId(
        roomId: UUID,
        userId: UUID
    ): Mono<Void> =
        dao.deleteByRoomIdAndUserId(roomId, userId)
             .then()
}