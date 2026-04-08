package com.example.fluxstudy.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    val accessExpirationMinutes: Long,
    val refreshExpirationDays: Long,
){
    val accessExpiration: Duration
        get() = Duration.ofMinutes(accessExpirationMinutes)

    val refreshExpiration: Duration
        get() = Duration.ofDays(refreshExpirationDays)
}
