package com.example.fluxstudy.chat.message.api.router

import com.example.fluxstudy.chat.message.api.handler.MessageHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MessageRouter (
    private val messageHandler: MessageHandler
){
    @Bean
    fun messageRoutes(): RouterFunction<ServerResponse> = coRouter {
        "/api/v1/rooms/{roomId}".nest {
            POST("/messages", messageHandler::sendMessage)
            GET("/messages", messageHandler::getMessages)
        }
    }
}