package com.example.fluxstudy.chat.room.api.usecase

import com.example.fluxstudy.chat.room.api.dto.request.GetRoomRequest
import com.example.fluxstudy.chat.room.api.dto.response.GetRoomResponse
import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import com.example.fluxstudy.chat.room.domain.service.ChatRoomService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class GetRoomUseCase (
    private val chatRoomService: ChatRoomService
){
    fun execute(request: GetRoomRequest, userId: UUID): Mono<GetRoomResponse> =
        chatRoomService.validateMember(request.roomId, userId)
            .flatMap { chatRoomService.getRoom(request.roomId) }
            .map { room ->
                GetRoomResponse(
                    id = room.id,
                    type = when (room) {
                        is ChatRoom.Direct -> "DIRECT"
                        is ChatRoom.Group -> "GROUP"
                    },
                    name = when (room) {
                        is ChatRoom.Direct -> null
                        is ChatRoom.Group -> room.name
                    },
                    ownerId = room.ownerId,
                    createdAt = room.createdAt,
                )
            }
}