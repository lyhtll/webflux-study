package com.example.fluxstudy.chat.room.domain.repository

import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface ChatRoomRepository {
    fun findById(id: UUID): Mono<ChatRoom>
    fun findByFirstUserIdAndSecondUserId(firstUserId: UUID, secondUserId: UUID): Mono<ChatRoom>
    fun findAllByUserId(userId: UUID): Flux<ChatRoom>
    fun create(chatRoom: ChatRoom): Mono<ChatRoom>
}