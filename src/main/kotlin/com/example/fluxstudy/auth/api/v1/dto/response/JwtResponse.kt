package com.example.fluxstudy.auth.api.v1.dto.response

import com.example.fluxstudy.security.jwt.AccessToken
import com.example.fluxstudy.security.jwt.RefreshToken
import java.time.Instant

data class JwtResponse(
    val value: String,
    val expiresAt: Instant,
){
    constructor(accessToken: AccessToken) : this(
        value = accessToken.value,
        expiresAt = accessToken.expiresAt,
    )

    constructor(refreshToken: RefreshToken) : this(
        value = refreshToken.value,
        expiresAt = refreshToken.expiresAt,
    )
}
