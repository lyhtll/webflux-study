package com.example.fluxstudy.user.domain.service

import com.example.fluxstudy.user.domain.exception.UserAlreadyExistsException
import com.example.fluxstudy.user.domain.exception.UserNotFoundException
import com.example.fluxstudy.user.domain.model.User
import com.example.fluxstudy.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun create(user: User): Mono<User> =
        userRepository.existsByUsername(user.name)
            .flatMap { exists ->
                if (exists) Mono.error(UserAlreadyExistsException())
                else userRepository.save(user)
            }

    fun getByUsername(username: String): Mono<User> =
        userRepository.findByUsername(username)
            .switchIfEmpty(Mono.error(UserNotFoundException()))
}