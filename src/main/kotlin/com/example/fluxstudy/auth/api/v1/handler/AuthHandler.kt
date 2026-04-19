package com.example.fluxstudy.auth.api.v1.handler

import com.example.fluxstudy.auth.api.v1.dto.request.LoginRequest
import com.example.fluxstudy.auth.api.v1.dto.request.RefreshRequest
import com.example.fluxstudy.auth.api.v1.dto.request.RegisterRequest
import com.example.fluxstudy.auth.api.v1.usecase.LoginUseCase
import com.example.fluxstudy.auth.api.v1.usecase.LogoutUseCase
import com.example.fluxstudy.auth.api.v1.usecase.RefreshUseCase
import com.example.fluxstudy.auth.api.v1.usecase.RegisterUseCase
import com.example.fluxstudy.common.validation.validateOrThrow
import jakarta.validation.Validator
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.util.UUID

@Component
class AuthHandler(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val refreshUseCase: RefreshUseCase,
    private val validator : Validator
) {
    suspend fun register(request: ServerRequest): ServerResponse {
        val body = request.awaitBody<RegisterRequest>()
        validator.validateOrThrow(body)
        val response = registerUseCase.execute(body).awaitSingle()
        return ServerResponse.status(HttpStatus.CREATED).bodyValueAndAwait(response)
    }

    suspend fun login(request: ServerRequest): ServerResponse {
        val body = request.awaitBody<LoginRequest>()
        validator.validateOrThrow(body)
        val response = loginUseCase.execute(body).awaitSingle()
        return ServerResponse.ok().bodyValueAndAwait(response)
    }

    suspend fun logout(request: ServerRequest): ServerResponse {
        val userId = request.principal().awaitSingle().name.let { UUID.fromString(it) }
        logoutUseCase.execute(userId).awaitSingleOrNull()
        return ServerResponse.noContent().buildAndAwait()
    }

    suspend fun refresh(request: ServerRequest): ServerResponse {
        val body = request.awaitBody<RefreshRequest>()
        validator.validateOrThrow(body)
        val response = refreshUseCase.execute(body).awaitSingle()
        return ServerResponse.ok().bodyValueAndAwait(response)
    }
}