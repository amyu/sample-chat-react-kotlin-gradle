package infra.chat_room

import dev.gitlive.firebase.firebase
import domain.chat_room.ChatRoom
import domain.chat_room.ChatRoomId
import domain.chat_room.ObserveChatRoomService
import domain.user.GetMeService
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import infra.user.UserConverter
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ObserveChatRoomServiceImpl : ObserveChatRoomService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()

    override fun observe(chatRoomId: ChatRoomId): Flow<ChatRoom> = callbackFlow {
        val firestore = FirebaseAppWithOptions.firestore
        val unsubscribe = firestore
            .collection("chatrooms")
            .doc(chatRoomId.rawValue)
            .onSnapshot(next = { document ->
                async {
                    val meUser = getMeService.get()

                    val joinedUser =
                        (document.get("joinedUsers") as Array<firebase.firestore.DocumentReference>)
                            .map {
                                val userDocumentSnapshot = it.get().await()
                                UserConverter.convertFromDocument(meUser?.id, userDocumentSnapshot)
                            }

                    val chatroom = ChatRoomConverter.convertFromDocument(document, joinedUser)
                    offer(chatroom)
                }
            }, error = {

            })

        awaitClose {
            unsubscribe()
        }
    }
}