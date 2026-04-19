package com.example.fluxstudy.chat.message.api.dto.response

import java.util.UUID

data class SendMessageResponse(
    val id: String,
    val roomId: UUID,
    val senderId: UUID,
    val content: String,
)
