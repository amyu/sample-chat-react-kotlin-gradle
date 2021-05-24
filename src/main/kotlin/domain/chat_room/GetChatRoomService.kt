package domain.chat_room

interface GetChatRoomService {
    suspend fun get(chatRoomId: ChatRoomId): ChatRoom
}