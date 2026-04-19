package com.example.fluxstudy.chat.message.api.handler

import com.example.fluxstudy.chat.message.api.dto.request.GetMessagesRequest
import com.example.fluxstudy.chat.message.api.dto.request.SendMessageRequest
import com.example.fluxstudy.chat.message.api.usecase.GetMessagesUseCase
import com.example.fluxstudy.chat.message.api.usecase.SendMessageUseCase
import com.example.fluxstudy.common.validation.validateOrThrow
import jakarta.validation.Validator
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.UUID

@Component
class MessageHandler(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val validator: Validator,
) {
    suspend fun sendMessage(request: ServerRequest): ServerResponse {
        val userId = request.principal().awaitSingle().name.let { UUID.fromString(it) }
        val body = request.awaitBody<SendMessageRequest>()
        validator.validateOrThrow(body)
        val response = sendMessageUseCase.execute(body, userId).awaitSingle()
        return ServerResponse.ok().bodyValueAndAwait(response)
    }

    suspend fun getMessages(request: ServerRequest): ServerResponse {
        val userId = request.principal().awaitSingle().name.let { UUID.fromString(it) }
        val roomId = UUID.fromString(request.pathVariable("roomId"))
        val lastMessageId = request.queryParam("lastMessageId").orElse(null)
        val limit = request.queryParam("limit").map { it.toInt() }.orElse(50)
        val messages = getMessagesUseCase.execute(
            request = GetMessagesRequest(
                roomId = roomId,
                lastMessageId = lastMessageId,
                limit = limit,
            ),
            userId = userId,
        )
        return ServerResponse.ok().bodyAndAwait(messages.asFlow())
    }
}