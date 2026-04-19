package com.example.fluxstudy.chat.message.api.dto.request

import java.util.UUID

data class SendMessageRequest(
    val roomId: UUID,
    val content: String,
)
