package infra.chat_room

import dev.gitlive.firebase.firebase
import domain.chat_room.AddChatRoomService
import infra.firebase.FirebaseAppWithOptions
import kotlinx.coroutines.await
import kotlinx.css.rgba
import kotlin.js.json
import kotlin.random.Random

class AddChatRoomServiceImpl : AddChatRoomService {
    override suspend fun add(roomName: String) {
        val emptyChat = json()

        val chat = FirebaseAppWithOptions.firestore
            .collection("chats")
            .add(emptyChat)
            .await()

        val newRoom = json(
            "roomName" to roomName,
            "joinedUsers" to emptyArray<String>(),
            "chat" to chat,
            // テストが書きづらくなるから本当はだめ
            "roomColor" to rgba(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), 0.6).value,
            "createdAt" to firebase.firestore.FieldValue.serverTimestamp(),
        )

        FirebaseAppWithOptions.firestore
            .collection("chatrooms")
            .add(newRoom)
            .await()
    }
}