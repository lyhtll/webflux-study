package com.example.fluxstudy.chat.room.api.dto.request

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CreateDirectRoomRequest(
    @field:NotNull(message = "The other party's ID is required.")
    val targetUserId: UUID,
)
