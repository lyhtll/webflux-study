package com.example.fluxstudy.chat.message.api.dto.request

import java.util.UUID

data class GetMessagesRequest(
    val roomId: UUID,
    val lastMessageId: String? = null,  // null이면 최신 메시지부터 가져옴
    val limit: Int,
)
