package com.example.fluxstudy.auth.api.v1.dto.response

data class RegisterResponse(
    val accessToken: JwtResponse,
    val refreshToken: JwtResponse,
)
