package com.example.fluxstudy.chat.room.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.util.UUID

data class CreateGroupRoomRequest(
    @field:NotBlank(message = "Chat room name is required.")
    @field:Size(min = 1, max = 100, message = "Chat room name must be 1 to 100 characters long.")
    val name: String,

    @field:NotEmpty(message = "There must be at least one member.")
    val memberIds: List<UUID>,
)
