package com.example.fluxstudy.chat.message.domain.repository

import com.example.fluxstudy.chat.message.domain.model.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface MessageRepository {
    fun save(message: Message): Mono<Message>
    fun findAllByRoomId(roomId: UUID, lastMessageId: String?, limit: Int): Flux<Message>
}