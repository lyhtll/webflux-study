package com.example.fluxstudy.chat.room.domain.exception

import com.example.fluxstudy.common.exception.AppException

class ChatRoomNotFoundException : AppException(
    code = "CHAT_ROOM_NOT_FOUND",
    message = "채팅방을 찾을 수 없습니다",
)

class ChatRoomAlreadyExistsException : AppException(
    code = "CHAT_ROOM_ALREADY_EXISTS",
    message = "이미 존재하는 채팅방입니다",
)

class ChatRoomMemberLimitException : AppException(
    code = "CHAT_ROOM_MEMBER_LIMIT",
    message = "채팅방 최대 인원을 초과했습니다",
)

class NotChatRoomMemberException : AppException(
    code = "NOT_CHAT_ROOM_MEMBER",
    message = "채팅방 멤버가 아닙니다",
)

class SelfChatNotAllowedException : AppException(
    code = "SELF_CHAT_NOT_ALLOWED",
    message = "자기 자신과 채팅할 수 없습니다",
)