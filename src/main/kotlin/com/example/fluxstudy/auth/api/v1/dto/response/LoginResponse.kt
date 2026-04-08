package com.example.fluxstudy.auth.api.v1.dto.response

import java.util.UUID


data class LoginResponse(
    val accessToken: JwtResponse,
    val refreshToken: JwtResponse,
    val user: User,
){
    data class User(
        val id: UUID,
        val username: String,
    )
}
