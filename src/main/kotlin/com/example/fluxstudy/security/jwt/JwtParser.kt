package com.example.fluxstudy.security.jwt

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtParser(
    secretKey: SecretKey,
) {
    private val parser = Jwts.parser()
        .verifyWith(secretKey)
        .build()

    fun extractClaims(token: String): JwtClaims {
        val claims = parser.parseSignedClaims(token).payload
        val type = JwtType.valueOf(claims["type"].toString().uppercase())
        val userId = UUID.fromString(claims.subject)
        val issuedAt = claims.issuedAt.toInstant()
        val expiresAt = claims.expiration.toInstant()

        return when (type) {
            JwtType.ACCESS -> JwtClaims.Access(
                userId = userId,
                issuedAt = issuedAt,
                expiresAt = expiresAt,
            )
            JwtType.REFRESH -> JwtClaims.Refresh(
                userId = userId,
                issuedAt = issuedAt,
                expiresAt = expiresAt,
            )
        }
    }
}