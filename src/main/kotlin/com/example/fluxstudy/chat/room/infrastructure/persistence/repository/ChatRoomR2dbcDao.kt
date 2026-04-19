package com.example.fluxstudy.chat.room.infrastructure.persistence.repository

import com.example.fluxstudy.chat.room.infrastructure.persistence.entity.ChatRoomEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface ChatRoomR2dbcDao : ReactiveCrudRepository<ChatRoomEntity, UUID> {
    fun findByFirstUserIdAndSecondUserId(firstUserId: UUID, secondUserId: UUID): Mono<ChatRoomEntity>
    @Query("""
        SELECT cr.* FROM chat_rooms cr
        INNER JOIN chat_room_members crm ON cr.id = crm.room_id
        WHERE crm.user_id = $1
    """)
    fun findAllByUserId(userId: UUID): Flux<ChatRoomEntity>
}