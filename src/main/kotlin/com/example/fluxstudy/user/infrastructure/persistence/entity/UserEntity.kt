package com.example.fluxstudy.user.infrastructure.persistence.entity

import com.example.fluxstudy.user.domain.model.User
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("users")
data class UserEntity(
    @Id val id: UUID,
    @Column("username")
    val username: String,
    @Column("password")
    val password: String,
    @Column("created_at")
    val createdAt: LocalDateTime,
)
fun UserEntity.toDomain(): User =
    User(
        id = id,
        name = username,
        password = password,
        createdAt = createdAt,
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = id,
        username = name,
        password = password,
        createdAt = createdAt,
    )