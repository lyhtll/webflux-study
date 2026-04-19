package com.example.fluxstudy.common.config

import com.example.fluxstudy.chat.websocket.ChatWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler

@Configuration
class WebSocketConfig(
    private val chatWebSocketHandler: ChatWebSocketHandler
) {
    @Bean
    fun webSocketHandlerMapping() : HandlerMapping {
        val map = mapOf<String, WebSocketHandler>(
            "/ws/chat/*" to chatWebSocketHandler
        )
        return SimpleUrlHandlerMapping(map, -1)
    }
}