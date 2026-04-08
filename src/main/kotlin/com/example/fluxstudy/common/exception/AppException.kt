package com.example.fluxstudy.common.exception

abstract class AppException (
    val code: String,
    override val message: String,
) : RuntimeException(message)