package com.example.fluxstudy.notification

import com.example.fluxstudy.chat.websocket.RedisChatService
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class NotificationHandler (
    private val redisChatService: RedisChatService,
){
    suspend fun subscribe(request: ServerRequest): ServerResponse {
        val roomId = UUID.fromString(request.pathVariable("roomId"))

        val userId = ReactiveSecurityContextHolder.getContext()
            .flatMap { context ->
                val name = context.authentication?.name
                if (name != null) Mono.just(name)
                else Mono.error(AuthenticationCredentialsNotFoundException("Unauthenticated"))
            }
            .awaitSingle()

        val stream: Flux<ServerSentEvent<String>> = redisChatService.subscribe(roomId)
            .map { message ->
                ServerSentEvent.builder<String>()
                    .id(UUID.randomUUID().toString())
                    .event("message")
                    .data(message)
                    .build()
            }

        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .bodyAndAwait(stream.asFlow())
    }
}