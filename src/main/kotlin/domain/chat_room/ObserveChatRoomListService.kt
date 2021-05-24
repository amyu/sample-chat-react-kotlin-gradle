package domain.chat_room

import kotlinx.coroutines.flow.Flow

interface ObserveChatRoomListService {
    fun observe(): Flow<List<ChatRoom>>
}