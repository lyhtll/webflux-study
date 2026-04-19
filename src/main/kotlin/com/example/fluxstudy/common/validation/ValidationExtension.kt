package com.example.fluxstudy.common.validation

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator

fun <T> Validator.validateOrThrow(target: T) {
    val errors = validate(target)
    if (errors.isNotEmpty()) {
        throw ConstraintViolationException(errors)
    }
}