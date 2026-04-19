package com.example.fluxstudy.chat.message.api.usecase

import com.example.fluxstudy.chat.message.api.dto.request.GetMessagesRequest
import com.example.fluxstudy.chat.message.api.dto.response.GetMessagesResponse
import com.example.fluxstudy.chat.message.domain.service.MessageService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.UUID

@Component
class GetMessagesUseCase(
    private val messageService: MessageService,
) {
    fun execute(request: GetMessagesRequest, userId: UUID): Flux<GetMessagesResponse> =
        messageService.getMessages(
            roomId = request.roomId,
            userId = userId,
            lastMessageId = request.lastMessageId,
            limit = request.limit,
        ).map { message ->
            GetMessagesResponse(
                id = message.id!!,
                roomId = message.roomId,
                senderId = message.senderId,
                content = message.content,
                createdAt = message.createdAt,
            )
        }
}