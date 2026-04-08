package com.example.fluxstudy.user.infrastructure.persistence.repository

import com.example.fluxstudy.user.infrastructure.persistence.entity.UserEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface UserR2dbcDao : ReactiveCrudRepository<UserEntity, UUID> {
    fun findByUsername(username: String): Mono<UserEntity>
    fun existsByUsername(username: String): Mono<Boolean>
}