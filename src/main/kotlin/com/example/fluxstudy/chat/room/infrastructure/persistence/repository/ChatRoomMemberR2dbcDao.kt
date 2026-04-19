package com.example.fluxstudy.chat.room.infrastructure.persistence.repository

import com.example.fluxstudy.chat.room.infrastructure.persistence.entity.ChatRoomMemberEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface ChatRoomMemberR2dbcDao : ReactiveCrudRepository<ChatRoomMemberEntity, UUID> {
    fun findAllByRoomId(roomId: UUID): Flux<ChatRoomMemberEntity>
    fun findByRoomIdAndUserId(roomId: UUID, userId: UUID): Mono<ChatRoomMemberEntity>
    fun existsByRoomIdAndUserId(roomId: UUID, userId: UUID): Mono<Boolean>
    fun deleteByRoomIdAndUserId(roomId: UUID, userId: UUID): Mono<Void>
}