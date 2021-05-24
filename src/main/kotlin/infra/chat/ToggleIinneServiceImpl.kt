package infra.chat

import dev.gitlive.firebase.firebase
import domain.chat.CanIine
import domain.chat.Chat
import domain.chat.ToggleIinneService
import domain.chat_room.ChatRoomId
import domain.user.GetMeService
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import kotlinx.coroutines.await

class ToggleIinneServiceImpl : ToggleIinneService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()

    override suspend fun toggle(
        chatRoomId: ChatRoomId,
        chat: Chat,
    ) {
        if (chat !is CanIine) {
            return
        }

        val me = getMeService.get() ?: return

        val firestore = FirebaseAppWithOptions.firestore
        val chatReference = firestore
            .collection("chatrooms")
            .doc(chatRoomId.rawValue)
            .get()
            .await()
            .let { document ->
                document.get("chat") as firebase.firestore.DocumentReference
            }


        val isAlreadyIine = chat.isIined(me.id)

        val iineUsersReference = chat.iineUsers.map { it.id }.let {
            if (isAlreadyIine) {
                it - me.id
            } else {
                it + me.id
            }
        }.map {
            FirebaseAppWithOptions.firestore.doc("users/${it}")
        }.toTypedArray()

        chatReference
            .collection("chat")
            .doc(chat.id.rawValue)
            .update("iineUsers", iineUsersReference)
            .await()
    }
}