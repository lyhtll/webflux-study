package com.example.fluxstudy.common.exception

import com.example.fluxstudy.user.domain.exception.InvalidCredentialsException
import com.example.fluxstudy.user.domain.exception.UserAlreadyExistsException
import com.example.fluxstudy.user.domain.exception.UserNotFoundException
import org.springframework.http.HttpStatus

val AppException.httpStatus: HttpStatus
    get() = when (this) {
        is UserNotFoundException -> HttpStatus.NOT_FOUND
        is UserAlreadyExistsException -> HttpStatus.CONFLICT
        is InvalidCredentialsException -> HttpStatus.UNAUTHORIZED
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }