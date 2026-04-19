package com.example.fluxstudy.chat.room.domain.service

import com.example.fluxstudy.chat.room.domain.exception.ChatRoomNotFoundException
import com.example.fluxstudy.chat.room.domain.exception.NotChatRoomMemberException
import com.example.fluxstudy.chat.room.domain.model.ChatRoom
import com.example.fluxstudy.chat.room.domain.model.ChatRoomMember
import com.example.fluxstudy.chat.room.domain.repository.ChatRoomMemberRepository
import com.example.fluxstudy.chat.room.domain.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomMemberRepository: ChatRoomMemberRepository,
)  {
    fun getOrCreateDirectRoom(firstUserId: UUID, secondUserId: UUID): Mono<ChatRoom> {
        val (sortedFirst, sortedSecond) = sortUserIds(firstUserId, secondUserId)

        return findDirectRoom(sortedFirst, sortedSecond)
            .switchIfEmpty(
                chatRoomRepository.create(
                    ChatRoom.Direct(
                        ownerId = firstUserId,
                        firstUserId = sortedFirst,
                        secondUserId = sortedSecond,
                    )
                ).flatMap { room ->
                    chatRoomMemberRepository.saveAll(
                        listOf(
                            ChatRoomMember(roomId = room.id, userId = firstUserId),
                            ChatRoomMember(roomId = room.id, userId = secondUserId),
                        )
                    ).then(Mono.just(room))
                }.onErrorResume { findDirectRoom(sortedFirst, sortedSecond) }
            )
    }

    fun createGroupRoom(name: String, ownerId: UUID, memberIds: List<UUID>): Mono<ChatRoom> {
        val allMemberIds = (memberIds + ownerId).distinct()

        return chatRoomRepository.create(
            ChatRoom.Group(name = name, ownerId = ownerId)
        ).flatMap { room ->
            chatRoomMemberRepository.saveAll(
                allMemberIds.map { ChatRoomMember(roomId = room.id, userId = it) }
            ).then(Mono.just(room))
        }
    }

    fun getRoom(roomId: UUID): Mono<ChatRoom> =
        chatRoomRepository.findById(roomId)
            .switchIfEmpty(Mono.error(ChatRoomNotFoundException()))

    fun getMyRooms(userId: UUID): Flux<ChatRoom> =
        chatRoomRepository.findAllByUserId(userId)

    fun validateMember(roomId: UUID, userId: UUID): Mono<Boolean> =
        chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, userId)
            .filter { it }
            .switchIfEmpty(Mono.error(NotChatRoomMemberException()))

    private fun findDirectRoom(firstUserId: UUID, secondUserId: UUID): Mono<ChatRoom> =
        chatRoomRepository.findByFirstUserIdAndSecondUserId(firstUserId, secondUserId)

    private fun sortUserIds(a: UUID, b: UUID): Pair<UUID, UUID> =
        if (a < b) Pair(a, b) else Pair(b, a)
}