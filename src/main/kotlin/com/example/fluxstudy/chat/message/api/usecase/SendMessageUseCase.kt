package com.example.fluxstudy.chat.message.api.usecase

import com.example.fluxstudy.chat.message.api.dto.request.SendMessageRequest
import com.example.fluxstudy.chat.message.api.dto.response.SendMessageResponse
import com.example.fluxstudy.chat.message.domain.service.MessageService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class SendMessageUseCase(
    private val messageService: MessageService,
) {
    fun execute(request: SendMessageRequest, senderId: UUID): Mono<SendMessageResponse> =
        messageService.sendMessage(
            roomId = request.roomId,
            senderId = senderId,
            content = request.content,
        ).map { message ->
            SendMessageResponse(
                id = message.id!!,
                roomId = message.roomId,
                senderId = message.senderId,
                content = message.content,
            )
        }
}
