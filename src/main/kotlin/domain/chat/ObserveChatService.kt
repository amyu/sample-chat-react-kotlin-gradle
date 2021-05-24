package domain.chat

import domain.chat_room.ChatRoomId
import kotlinx.coroutines.flow.Flow

interface ObserveChatService {
    fun observe(chatRoomId: ChatRoomId): Flow<List<Chat>>
}