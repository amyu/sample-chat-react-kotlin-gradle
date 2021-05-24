package domain.chat_room

interface AddChatRoomService {
    suspend fun add(roomName: String)
}