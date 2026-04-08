package com.example.fluxstudy.security.jwt

import java.time.Instant
import java.util.UUID

sealed interface JwtClaims {
    val userId : UUID
    val issuedAt : Instant
    val expiresAt : Instant

    data class Access(
        override val userId: UUID,
        override val issuedAt: Instant,
        override val expiresAt: Instant,
    ) : JwtClaims

    data class Refresh(
        override val userId: UUID,
        override val issuedAt: Instant,
        override val expiresAt: Instant,
    ) : JwtClaims

    fun requireAccess(): Access =
        this as? Access ?: throw IllegalStateException("token type must be ACCESS")

    fun requireRefresh(): Refresh =
        this as? Refresh ?: throw IllegalStateException("token type must be REFRESH")
}