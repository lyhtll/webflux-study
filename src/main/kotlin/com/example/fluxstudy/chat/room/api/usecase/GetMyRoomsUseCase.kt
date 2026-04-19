package com.example.fluxstudy.chat.room.api.usecase

import com.example.fluxstudy.chat.room.api.dto.response.GetMyRoomsResponse
import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import com.example.fluxstudy.chat.room.domain.service.ChatRoomService
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.UUID

@Component
class GetMyRoomsUseCase(
    private val chatRoomService: ChatRoomService,
) {
    fun execute(userId: UUID): Flux<GetMyRoomsResponse> =
        chatRoomService.getMyRooms(userId)
            .map { room ->
                GetMyRoomsResponse(
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