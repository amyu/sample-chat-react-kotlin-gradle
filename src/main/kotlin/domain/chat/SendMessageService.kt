package domain.chat

import domain.chat_room.ChatRoomId

interface SendMessageService {
    suspend fun send(
        chatRoomId: ChatRoomId,
        message: String,
    )
}