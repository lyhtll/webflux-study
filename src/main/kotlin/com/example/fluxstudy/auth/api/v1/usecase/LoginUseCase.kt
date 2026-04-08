package com.example.fluxstudy.auth.api.v1.usecase

import com.example.fluxstudy.auth.api.v1.dto.request.LoginRequest
import com.example.fluxstudy.auth.api.v1.dto.response.JwtResponse
import com.example.fluxstudy.auth.api.v1.dto.response.LoginResponse
import com.example.fluxstudy.security.jwt.JwtProvider
import com.example.fluxstudy.security.jwt.RefreshTokenStore
import com.example.fluxstudy.user.domain.exception.InvalidCredentialsException
import com.example.fluxstudy.user.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class LoginUseCase (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val refreshTokenStore: RefreshTokenStore,
){
    fun execute(request: LoginRequest): Mono<LoginResponse> =
        userRepository.findByUsername(request.username)
            .filter { passwordEncoder.matches(request.password, it.password) }
            .switchIfEmpty(Mono.error(InvalidCredentialsException()))
            .flatMap { user ->
                val accessToken = jwtProvider.generateAccessToken(user.id)
                val refreshToken = jwtProvider.generateRefreshToken(user.id)

                refreshTokenStore.store(user.id, refreshToken.value)
                    .map {
                        LoginResponse(
                            accessToken = JwtResponse(accessToken),
                            refreshToken = JwtResponse(refreshToken),
                            user = LoginResponse.User(
                                id = user.id,
                                username = user.name,
                            )
                        )
                    }
            }
}