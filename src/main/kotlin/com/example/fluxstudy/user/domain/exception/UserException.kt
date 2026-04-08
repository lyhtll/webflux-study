package com.example.fluxstudy.user.domain.exception

import com.example.fluxstudy.common.exception.AppException

class UserNotFoundException : AppException(
    code = "USER_NOT_FOUND",
    message = "유저를 찾을 수 없습니다",
)

class UserAlreadyExistsException : AppException(
    code = "USER_ALREADY_EXISTS",
    message = "이미 존재하는 유저입니다",
)

class InvalidCredentialsException : AppException(
    code = "INVALID_CREDENTIALS",
    message = "아이디 또는 비밀번호가 올바르지 않습니다",
)