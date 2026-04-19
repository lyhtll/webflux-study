package com.example.fluxstudy.chat.room.api.router

import com.example.fluxstudy.chat.room.api.handler.ChatRoomHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ChatRoomRouter(
    private val chatRoomHandler: ChatRoomHandler
) {
    @Bean
    fun chatRoomRoutes(): RouterFunction<ServerResponse> = coRouter {
        "/api/v1/rooms".nest {
            POST("/direct", chatRoomHandler::createDirectRoom)
            POST("/group", chatRoomHandler::createGroupRoom)
            GET("/me", chatRoomHandler::getMyRooms)
            GET("/{roomId}", chatRoomHandler::getRoom)
        }
    }
}