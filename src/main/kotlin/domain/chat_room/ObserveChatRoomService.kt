package domain.chat_room

import kotlinx.coroutines.flow.Flow

interface ObserveChatRoomService {
    fun observe(chatRoomId: ChatRoomId): Flow<ChatRoom>
}