package com.example.fluxstudy.chat.room.api.usecase

import com.example.fluxstudy.chat.room.api.dto.request.CreateDirectRoomRequest
import com.example.fluxstudy.chat.room.api.dto.response.CreateDirectRoomResponse
import com.example.fluxstudy.chat.room.domain.exception.SelfChatNotAllowedException
import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import com.example.fluxstudy.chat.room.domain.service.ChatRoomService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class CreateDirectRoomUseCase (
    private val chatRoomService: ChatRoomService
){
    fun execute(request: CreateDirectRoomRequest, targetUserId: UUID): Mono<CreateDirectRoomResponse> {
        if (request.targetUserId == targetUserId)
            return Mono.error(SelfChatNotAllowedException())

        return chatRoomService.getOrCreateDirectRoom(targetUserId, request.targetUserId)
            .map { room ->
                val direct = room as ChatRoom.Direct
                CreateDirectRoomResponse(
                    id = direct.id,
                    firstUserId = direct.firstUserId,
                    secondUserId = direct.secondUserId,
                    createdAt = direct.createdAt,
                )
            }
    }
}