package com.example.fluxstudy.security.jwt

import java.time.Instant

data class AccessToken(
    val value: String,
    val expiresAt: Instant,
)
