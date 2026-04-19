package com.example.fluxstudy.chat.room.api.usecase

import com.example.fluxstudy.chat.room.api.dto.request.CreateGroupRoomRequest
import com.example.fluxstudy.chat.room.api.dto.response.CreateGroupRoomResponse
import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import com.example.fluxstudy.chat.room.domain.service.ChatRoomService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class CreateGroupRoomUseCase (
    private val chatRoomService: ChatRoomService
){
    fun execute(request: CreateGroupRoomRequest, ownerId: UUID): Mono<CreateGroupRoomResponse> =
        chatRoomService.createGroupRoom(
            name = request.name,
            ownerId = ownerId,
            memberIds = request.memberIds,
        ).map { room ->
            val group = room as ChatRoom.Group
            CreateGroupRoomResponse(
                id = group.id,
                name = group.name,
                ownerId = group.ownerId,
                createdAt = group.createdAt,
            )
        }
}