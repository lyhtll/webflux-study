package com.example.fluxstudy.auth.api.v1.usecase

import com.example.fluxstudy.security.jwt.RefreshTokenStore
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class LogoutUseCase (
    private val refreshTokenStore: RefreshTokenStore
){
    fun execute(
        userId: UUID,
    ) = refreshTokenStore.delete(userId)
}