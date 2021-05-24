package infra.chat_room

import dev.gitlive.firebase.firebase
import domain.chat_room.ChatRoom
import domain.chat_room.ObserveChatRoomListService
import domain.user.GetMeService
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import infra.user.UserConverter
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ObserveChatRoomListServiceImpl : ObserveChatRoomListService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()

    override fun observe(): Flow<List<ChatRoom>> = callbackFlow {
        val firestore = FirebaseAppWithOptions.firestore
        val unsubscribe = firestore
            .collection("chatrooms")
            .orderBy("createdAt", "asc")
            .onSnapshot(next = {
                async {
                    val meUser = getMeService.get()

                    it.docs.map { document ->
                        val joinedUser =
                            (document.get("joinedUsers") as Array<firebase.firestore.DocumentReference>)
                                .map {
                                    val userDocumentSnapshot = it.get().await()
                                    UserConverter.convertFromDocument(meUser?.id, userDocumentSnapshot)
                                }

                        ChatRoomConverter.convertFromDocument(document, joinedUser)
                    }.let {
                        offer(it)
                    }
                }
            }, error = {

            })

        awaitClose {
            unsubscribe()
        }
    }
}
