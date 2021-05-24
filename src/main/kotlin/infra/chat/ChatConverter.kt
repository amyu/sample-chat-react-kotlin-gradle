package infra.chat

import dev.gitlive.firebase.firebase
import domain.chat.Chat
import domain.chat.ChatId
import domain.user.User

object ChatConverter {
    fun convertFromDocument(
        chatDocumentSnapshot: firebase.firestore.DocumentSnapshot,
        iineUsers: List<User>,
        sendUser: User,
    ): Chat = when (ChatType.fromRawValue(chatDocumentSnapshot.get("type") as Int)) {
        ChatType.MESSAGE -> Chat.Message(
            id = ChatId(chatDocumentSnapshot.id),
            message = chatDocumentSnapshot.get("message") as String,
            iineUsers = iineUsers,
            sendUser = sendUser
        )
        ChatType.IMAGE -> Chat.Image(
            id = ChatId(chatDocumentSnapshot.id),
            imageUrl = chatDocumentSnapshot.get("imageUrl") as String,
            iineUsers = iineUsers,
            sendUser = sendUser
        )
    }
}