package infra.chat_room

import dev.gitlive.firebase.firebase
import domain.chat_room.ChatRoom
import domain.chat_room.ChatRoomId
import domain.chat_room.GetChatRoomService
import domain.user.GetMeService
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import infra.user.UserConverter
import kotlinx.coroutines.await

class GetChatRoomServiceImpl : GetChatRoomService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()

    override suspend fun get(chatRoomId: ChatRoomId): ChatRoom {
        val meUser = getMeService.get()

        val firestore = FirebaseAppWithOptions.firestore
        return firestore
            .collection("chatrooms")
            .doc(chatRoomId.rawValue)
            .get()
            .await()
            .let { document ->
                val joinedUser =
                    (document.get("joinedUsers") as Array<firebase.firestore.DocumentReference>)
                        .map {
                            val userDocumentSnapshot = it.get().await()
                            UserConverter.convertFromDocument(meUser?.id, userDocumentSnapshot)
                        }

                ChatRoomConverter.convertFromDocument(document, joinedUser)
            }
    }
}