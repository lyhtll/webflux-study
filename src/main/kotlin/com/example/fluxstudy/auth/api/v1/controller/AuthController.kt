package com.example.fluxstudy.auth.api.v1.controller

import com.example.fluxstudy.auth.api.v1.dto.request.LoginRequest
import com.example.fluxstudy.auth.api.v1.dto.request.RegisterRequest
import com.example.fluxstudy.auth.api.v1.dto.response.LoginResponse
import com.example.fluxstudy.auth.api.v1.dto.response.RegisterResponse
import com.example.fluxstudy.auth.api.v1.usecase.LoginUseCase
import com.example.fluxstudy.auth.api.v1.usecase.LogoutUseCase
import com.example.fluxstudy.auth.api.v1.usecase.RegisterUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth")
class AuthController (
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
){
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): Mono<ResponseEntity<RegisterResponse>> =
        registerUseCase.execute(request)
            .map { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): Mono<ResponseEntity<LoginResponse>> =
        loginUseCase.execute(request)
            .map { ResponseEntity.ok(it) }

    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal userId: UUID): Mono<ResponseEntity<Void>> =
        logoutUseCase.execute(userId)
            .then(Mono.just(ResponseEntity.noContent().build()))
}