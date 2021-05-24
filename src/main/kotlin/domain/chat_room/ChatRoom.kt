package domain.chat_room

import domain.Entity
import domain.user.User

data class ChatRoom(
    override val id: ChatRoomId,
    val roomName: String,
    val joinedUsers: List<User>,
    val roomColor: String,
) : Entity<ChatRoomId>(id)