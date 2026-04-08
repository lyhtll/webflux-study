package com.example.fluxstudy.auth.api.v1.usecase

import com.example.fluxstudy.auth.api.v1.dto.request.RegisterRequest
import com.example.fluxstudy.auth.api.v1.dto.response.JwtResponse
import com.example.fluxstudy.auth.api.v1.dto.response.RegisterResponse
import com.example.fluxstudy.security.jwt.JwtProvider
import com.example.fluxstudy.security.jwt.RefreshTokenStore
import com.example.fluxstudy.user.domain.model.User
import com.example.fluxstudy.user.domain.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RegisterUseCase (
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val refreshTokenStore: RefreshTokenStore
) {
    fun execute(request: RegisterRequest): Mono<RegisterResponse> {
        val hashedPassword = passwordEncoder.encode(request.password)!!

        return userService.create(
            User(
                name = request.username,
                password = hashedPassword,
            )
        ).flatMap { user ->
            val accessToken = jwtProvider.generateAccessToken(user.id)
            val refreshToken = jwtProvider.generateRefreshToken(user.id)

            refreshTokenStore.store(user.id, refreshToken.value)
                .map {
                    RegisterResponse(
                        accessToken = JwtResponse(accessToken),
                        refreshToken = JwtResponse(refreshToken),
                    )
                }
        }
    }

}