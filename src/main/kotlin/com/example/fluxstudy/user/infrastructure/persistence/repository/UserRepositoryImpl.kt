package com.example.fluxstudy.user.infrastructure.persistence.repository

import com.example.fluxstudy.user.domain.model.User
import com.example.fluxstudy.user.domain.repository.UserRepository
import com.example.fluxstudy.user.infrastructure.persistence.entity.toDomain
import com.example.fluxstudy.user.infrastructure.persistence.entity.toEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
class UserRepositoryImpl(
    private val dao: UserR2dbcDao,
) : UserRepository {
    override fun findById(id: UUID): Mono<User> =
        dao.findById(id)
            .map { it.toDomain() }

    override fun findByUsername(username: String): Mono<User> =
        dao.findByUsername(username)
            .map { it.toDomain() }

    override fun existsByUsername(username: String): Mono<Boolean> =
        dao.existsByUsername(username)

    override fun save(user: User): Mono<User> =
        dao.save(user.toEntity())
            .map { it.toDomain() }
}