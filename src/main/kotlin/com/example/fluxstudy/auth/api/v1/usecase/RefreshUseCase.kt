package com.example.fluxstudy.auth.api.v1.usecase

import com.example.fluxstudy.auth.api.v1.dto.request.RefreshRequest
import com.example.fluxstudy.auth.api.v1.dto.response.JwtResponse
import com.example.fluxstudy.auth.api.v1.dto.response.RefreshResponse
import com.example.fluxstudy.security.jwt.JwtParser
import com.example.fluxstudy.security.jwt.JwtProvider
import com.example.fluxstudy.security.jwt.RefreshTokenStore
import com.example.fluxstudy.user.domain.exception.InvalidCredentialsException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RefreshUseCase (
    private val jwtParser: JwtParser,
    private val refreshTokenStore: RefreshTokenStore,
    private val jwtProvider: JwtProvider,
){
    fun execute(request: RefreshRequest): Mono<RefreshResponse> =
        Mono.fromCallable { jwtParser.extractClaims(request.refreshToken).requireRefresh() }
            .flatMap { claims ->
                refreshTokenStore.get(claims.userId)
                    .switchIfEmpty(Mono.error(IllegalArgumentException("Invalid refresh token")))
                    .filter { it == request.refreshToken }
                    .switchIfEmpty(Mono.error(IllegalArgumentException("Invalid refresh token")))
                    .flatMap {
                        val accessToken = jwtProvider.generateAccessToken(claims.userId)
                        val refreshToken = jwtProvider.generateRefreshToken(claims.userId)

                        refreshTokenStore.store(claims.userId, refreshToken.value)
                            .map {
                                RefreshResponse(
                                    accessToken = JwtResponse(accessToken),
                                    refreshToken = JwtResponse(refreshToken),
                                )
                            }
                    }
            }
}