package com.example.fluxstudy.auth.api.v1.dto.request

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "Refresh token must not be blank")
    val refreshToken: String
)
