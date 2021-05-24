package domain.chat_room

import domain.chat_room.ChatRoomId

interface LeaveChatRoomService {
    suspend fun leave(chatRoomId: ChatRoomId)
}