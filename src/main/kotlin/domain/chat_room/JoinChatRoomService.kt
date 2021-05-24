package domain.chat_room

interface JoinChatRoomService {
    suspend fun join(chatRoomId: ChatRoomId)
}