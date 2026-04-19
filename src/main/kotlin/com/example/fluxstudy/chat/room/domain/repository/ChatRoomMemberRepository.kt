package com.example.fluxstudy.chat.room.domain.repository

import com.example.fluxstudy.chat.room.domain.model.ChatRoomMember
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface ChatRoomMemberRepository {
    fun findAllByRoomId(roomId: UUID): Flux<ChatRoomMember>
    fun findByRoomIdAndUserId(roomId: UUID, userId: UUID): Mono<ChatRoomMember>
    fun existsByRoomIdAndUserId(roomId: UUID, userId: UUID): Mono<Boolean>
    fun save(member: ChatRoomMember): Mono<ChatRoomMember>
    fun saveAll(members: List<ChatRoomMember>): Flux<ChatRoomMember>
    fun deleteByRoomIdAndUserId(roomId: UUID, userId: UUID): Mono<Void>
}