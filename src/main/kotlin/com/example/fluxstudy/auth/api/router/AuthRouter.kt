package com.example.fluxstudy.auth.api.router

import com.example.fluxstudy.auth.api.v1.handler.AuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthRouter(
    private val authHandler: AuthHandler
) {
    @Bean
    fun authRoutes(): RouterFunction<ServerResponse> = coRouter {
        "/api/v1/auth".nest {
            POST("/register", authHandler::register)
            POST("/login", authHandler::login)
            POST("/logout", authHandler::logout)
            POST("/refresh", authHandler::refresh)
        }
    }
}