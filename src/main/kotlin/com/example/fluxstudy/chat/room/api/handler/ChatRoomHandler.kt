package com.example.fluxstudy.chat.room.api.handler

import com.example.fluxstudy.chat.room.api.dto.request.CreateDirectRoomRequest
import com.example.fluxstudy.chat.room.api.dto.request.CreateGroupRoomRequest
import com.example.fluxstudy.chat.room.api.dto.request.GetRoomRequest
import com.example.fluxstudy.chat.room.api.dto.response.GetMyRoomsResponse
import com.example.fluxstudy.chat.room.api.usecase.CreateDirectRoomUseCase
import com.example.fluxstudy.chat.room.api.usecase.CreateGroupRoomUseCase
import com.example.fluxstudy.chat.room.api.usecase.GetMyRoomsUseCase
import com.example.fluxstudy.chat.room.api.usecase.GetRoomUseCase
import com.example.fluxstudy.common.validation.validateOrThrow
import jakarta.validation.Validator
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.bodyAndAwait
import reactor.core.publisher.Flux
import java.util.UUID

@Component
class ChatRoomHandler(
    private val createDirectRoomUseCase: CreateDirectRoomUseCase,
    private val createGroupRoomUseCase: CreateGroupRoomUseCase,
    private val getRoomUseCase: GetRoomUseCase,
    private val getMyRoomsUseCase: GetMyRoomsUseCase,
    private val validator: Validator,
) {
    suspend fun createDirectRoom(request: ServerRequest): ServerResponse {
        val userId = request.principal().awaitSingle().name.let { UUID.fromString(it) }
        val body = request.awaitBody<CreateDirectRoomRequest>()
        validator.validateOrThrow(body)
        val response = createDirectRoomUseCase.execute(body, userId).awaitSingle()
        return ServerResponse.status(HttpStatus.CREATED).bodyValueAndAwait(response)
    }

    suspend fun createGroupRoom(request: ServerRequest): ServerResponse {
        val userId = request.principal().awaitSingle().name.let { UUID.fromString(it) }
        val body = request.awaitBody<CreateGroupRoomRequest>()
        validator.validateOrThrow(body)
        val response = createGroupRoomUseCase.execute(body, userId).awaitSingle()
        return ServerResponse.status(HttpStatus.CREATED).bodyValueAndAwait(response)
    }

    suspend fun getRoom(request: ServerRequest): ServerResponse {
        val userId = request.principal().awaitSingle().name.let { UUID.fromString(it) }
        val roomId = UUID.fromString(request.pathVariable("roomId"))
        val response = getRoomUseCase.execute(GetRoomRequest(roomId), userId).awaitSingle()
        return ServerResponse.ok().bodyValueAndAwait(response)
    }

    suspend fun getMyRooms(request: ServerRequest): ServerResponse {
        val userId = request.principal().awaitSingle().name.let { UUID.fromString(it) }
        val rooms: Flux<GetMyRoomsResponse> = getMyRoomsUseCase.execute(userId)
        return ServerResponse.ok().bodyAndAwait(rooms.asFlow())
    }
}