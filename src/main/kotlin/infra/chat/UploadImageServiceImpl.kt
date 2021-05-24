package infra.chat

import dev.gitlive.firebase.firebase
import domain.chat.UploadImageService
import domain.chat_room.ChatRoomId
import domain.user.GetMeService
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import kotlinx.coroutines.await
import org.w3c.files.File
import kotlin.js.json

class UploadImageServiceImpl : UploadImageService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()
    override suspend fun upload(
        chatRoomId: ChatRoomId,
        file: File,
    ) {
        /**
         * Fileの取得までできている
         * Firebase Storageを使用したかったが､SDKの都合で実装ができなかった
         * 一旦Firebase StorageへUploadできた体で実装した
         */

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
            "imageUrl" to "https://picsum.photos/200/300",
            "sendUser" to FirebaseAppWithOptions.firestore.doc("users/${meUser.id}"),
            "type" to ChatType.IMAGE.raw,
            "createdAt" to firebase.firestore.FieldValue.serverTimestamp(),
        )

        chatReference
            .collection("chat")
            .add(newChat)
            .await()
    }
}