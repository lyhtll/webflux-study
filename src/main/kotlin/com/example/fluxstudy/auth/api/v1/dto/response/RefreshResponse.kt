package com.example.fluxstudy.auth.api.v1.dto.response

data class RefreshResponse(
    val accessToken: JwtResponse,
    val refreshToken: JwtResponse,
)
