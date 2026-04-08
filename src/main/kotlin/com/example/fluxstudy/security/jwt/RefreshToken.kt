package com.example.fluxstudy.security.jwt

import java.time.Instant

data class RefreshToken(
    val value: String,
    val expiresAt: Instant,
)
