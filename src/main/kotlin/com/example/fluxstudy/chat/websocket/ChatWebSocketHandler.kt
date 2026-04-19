package com.example.fluxstudy.chat.websocket

import com.example.fluxstudy.chat.message.domain.service.MessageService
import com.example.fluxstudy.common.logger.logger
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import tools.jackson.databind.ObjectMapper
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.util.*

@Component
class ChatWebSocketHandler(
    private val redisChatService: RedisChatService,
    private val messageService: MessageService,
    private val objectMapper: ObjectMapper,
) : WebSocketHandler {
    private val log by logger()

    override fun handle(session: WebSocketSession): Mono<Void> {
        val roomId = runCatching {
            UUID.fromString(session.handshakeInfo.uri.path.split("/").last())
        }.getOrElse {
            return session.close().then(Mono.empty())
        }

        return ReactiveSecurityContextHolder.getContext()
            .flatMap { context ->
                val senderId = context.authentication?.name?.let { UUID.fromString(it) }
                    ?: return@flatMap Mono.error(
                        AuthenticationCredentialsNotFoundException("Unauthenticated")
                    )

                val input = session.receive()
                    .flatMap { wsMessage ->
                        messageService.sendMessage(roomId, senderId, wsMessage.payloadAsText)
                            .flatMap { message ->
                                Mono.fromCallable {
                                    objectMapper.writeValueAsString(
                                        ChatMessage(
                                            messageId = message.id!!,
                                            senderId = message.senderId,
                                            content = message.content,
                                            createdAt = message.createdAt,
                                        )
                                    )
                                }.flatMap { redisChatService.publish(roomId, it) }
                            }
                            .onErrorResume { e ->
                                log.warn("메시지 처리 중 오류 발생: ${e.message}")
                                Mono.empty()
                            }
                    }
                    .then()

                val output = session.send(
                    redisChatService.subscribe(roomId)
                        .map { session.textMessage(it) }
                )

                Mono.zip(input, output).then()
            }
            .doFinally {
                log.info("WebSocket 연결 종료 — roomId: $roomId")
            }
    }
}