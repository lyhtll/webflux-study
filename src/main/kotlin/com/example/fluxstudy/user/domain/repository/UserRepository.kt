package com.example.fluxstudy.user.domain.repository

import com.example.fluxstudy.user.domain.model.User
import reactor.core.publisher.Mono
import java.util.UUID

interface UserRepository {
    fun findById(id: UUID): Mono<User>
    fun findByUsername(username: String): Mono<User>
    fun existsByUsername(username: String): Mono<Boolean>
    fun save(user: User): Mono<User>
}