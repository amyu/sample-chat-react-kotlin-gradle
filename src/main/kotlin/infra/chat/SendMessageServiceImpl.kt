package infra.chat

import dev.gitlive.firebase.firebase
import domain.chat.SendMessageService
import domain.chat_room.ChatRoomId
import domain.user.GetMeService
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import kotlinx.coroutines.await
import kotlin.js.json

class SendMessageServiceImpl: SendMessageService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()

    override suspend fun send(
        chatRoomId: ChatRoomId,
        message: String,
    ) {
        val firestore = FirebaseAppWithOptions.firestore
        val chatReference = firestore
            .collection("chatrooms")
            .doc(chatRoomId.rawValue)
            .get()
            .await()
            .let { document ->
                document.get("chat") as firebase.firestore.DocumentReference
            }
        val meUser = getMeService.get() ?: return

        val newChat = json(
            "iineUsers" to emptyArray<String>(),
            "message" to message,
            "sendUser" to FirebaseAppWithOptions.firestore.doc("users/${meUser.id}"),
            "type" to ChatType.MESSAGE.raw,
            "createdAt" to firebase.firestore.FieldValue.serverTimestamp(),
        )

        chatReference
            .collection("chat")
            .add(newChat)
            .await()
    }
}