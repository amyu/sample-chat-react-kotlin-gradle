package infra.chat_room

import dev.gitlive.firebase.firebase
import domain.chat_room.ChatRoom
import domain.chat_room.ChatRoomId
import domain.user.User

object ChatRoomConverter {
    fun convertFromDocument(
        chatRoomDocumentSnapshot: firebase.firestore.DocumentSnapshot,
        joinedUser: List<User>
    ): ChatRoom =
        ChatRoom(
            id = ChatRoomId(chatRoomDocumentSnapshot.id),
            roomName = chatRoomDocumentSnapshot.get("roomName") as String,
            roomColor = chatRoomDocumentSnapshot.get("roomColor") as String,
            joinedUsers = joinedUser,
        )
}