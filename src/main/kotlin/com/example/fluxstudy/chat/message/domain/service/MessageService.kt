package com.example.fluxstudy.chat.message.domain.service

import com.example.fluxstudy.chat.message.domain.model.Message
import com.example.fluxstudy.chat.message.domain.repository.MessageRepository
import com.example.fluxstudy.chat.room.domain.service.ChatRoomService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class MessageService (
    private val messageRepository: MessageRepository,
    private val chatRoomService: ChatRoomService,
) {
    fun sendMessage(roomId: UUID, senderId: UUID, content: String): Mono<Message> =
        chatRoomService.validateMember(roomId, senderId)
            .flatMap {
                messageRepository.save(
                    Message(
                        roomId = roomId,
                        senderId = senderId,
                        content = content,
                    )
                )
            }

    fun getMessages(roomId: UUID, userId: UUID, lastMessageId: String?, limit: Int): Flux<Message> =
        chatRoomService.validateMember(roomId, userId)
            .thenMany(messageRepository.findAllByRoomId(roomId, lastMessageId, limit))
}