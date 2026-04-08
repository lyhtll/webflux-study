package com.example.fluxstudy.security.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
class JwtConfig (
    private val jwtProperties: JwtProperties
){
    @Bean
    fun secretKey(): SecretKey {
        require(jwtProperties.secretKey.toByteArray().size >= 32) {
            "JWT secret must be at least 32 bytes"
        }
        return Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
    }
}