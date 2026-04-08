package com.example.fluxstudy.security.jwt

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val secretKey: SecretKey,
) {
    fun generateAccessToken(userId: UUID): AccessToken {
        val now = Instant.now()
        val expiresAt = now.plus(jwtProperties.accessExpiration)

        val token = Jwts.builder()
            .subject(userId.toString())
            .claim("type", JwtType.ACCESS.name.lowercase())
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiresAt))
            .signWith(secretKey)
            .compact()

        return AccessToken(
            value = token,
            expiresAt = expiresAt,
        )
    }

    fun generateRefreshToken(userId: UUID): RefreshToken {
        val now = Instant.now()
        val expiresAt = now.plus(jwtProperties.refreshExpiration)

        val token = Jwts.builder()
            .subject(userId.toString())
            .claim("type", JwtType.REFRESH.name.lowercase())
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiresAt))
            .signWith(secretKey)
            .compact()

        return RefreshToken(
            value = token,
            expiresAt = expiresAt,
        )
    }
}