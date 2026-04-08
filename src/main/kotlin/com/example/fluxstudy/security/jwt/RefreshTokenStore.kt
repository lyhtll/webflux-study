package com.example.fluxstudy.security.jwt

import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class RefreshTokenStore (
    private val redisTemplate: ReactiveStringRedisTemplate,
    private val jwtProperties: JwtProperties,
) {
    fun store(userId: UUID, refreshToken: String): Mono<Boolean> =
        redisTemplate.opsForValue()
            .set(key(userId), refreshToken, jwtProperties.refreshExpiration)
            .map { it }

    fun get(userId: UUID): Mono<String> =
        redisTemplate.opsForValue()
            .get(key(userId))

    fun delete(userId: UUID): Mono<Boolean> =
        redisTemplate.delete(key(userId))
            .map { it > 0 }

    private fun key(userId: UUID) = "$PREFIX$userId"

    companion object {
        private const val PREFIX = "refresh_token:"
    }
}