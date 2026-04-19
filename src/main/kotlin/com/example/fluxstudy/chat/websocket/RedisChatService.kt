package com.example.fluxstudy.chat.websocket

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class RedisChatService(
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val listenerContainer: ReactiveRedisMessageListenerContainer,
) {
    private fun channelKey(roomId: UUID) = "chat:room:$roomId"

    fun publish(roomId: UUID, message: String): Mono<Long> =
        redisTemplate.convertAndSend(channelKey(roomId), message)

    fun subscribe(roomId: UUID): Flux<String> =
        listenerContainer
            .receiveLater(ChannelTopic.of(channelKey(roomId)))
            .flatMapMany { flux -> flux.map { it.message } }
}