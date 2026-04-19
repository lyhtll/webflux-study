package com.example.fluxstudy.common.exception

import com.example.fluxstudy.chat.room.domain.exception.ChatRoomAlreadyExistsException
import com.example.fluxstudy.chat.room.domain.exception.ChatRoomMemberLimitException
import com.example.fluxstudy.chat.room.domain.exception.ChatRoomNotFoundException
import com.example.fluxstudy.chat.room.domain.exception.NotChatRoomMemberException
import com.example.fluxstudy.chat.room.domain.exception.SelfChatNotAllowedException
import com.example.fluxstudy.user.domain.exception.InvalidCredentialsException
import com.example.fluxstudy.user.domain.exception.UserAlreadyExistsException
import com.example.fluxstudy.user.domain.exception.UserNotFoundException
import org.springframework.http.HttpStatus

val AppException.httpStatus: HttpStatus
    get() = when (this) {
        is UserNotFoundException -> HttpStatus.NOT_FOUND
        is UserAlreadyExistsException -> HttpStatus.CONFLICT
        is InvalidCredentialsException -> HttpStatus.UNAUTHORIZED
        is ChatRoomNotFoundException -> HttpStatus.NOT_FOUND
        is ChatRoomAlreadyExistsException -> HttpStatus.CONFLICT
        is ChatRoomMemberLimitException -> HttpStatus.BAD_REQUEST
        is NotChatRoomMemberException -> HttpStatus.FORBIDDEN
        is SelfChatNotAllowedException -> HttpStatus.BAD_REQUEST
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }