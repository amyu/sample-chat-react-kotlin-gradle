package infra.chat

import dev.gitlive.firebase.firebase
import domain.chat.Chat
import domain.chat.ObserveChatService
import domain.chat_room.ChatRoomId
import domain.user.GetMeService
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import infra.user.UserConverter
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ObserveChatServiceImpl : ObserveChatService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()

    override fun observe(chatRoomId: ChatRoomId): Flow<List<Chat>> = callbackFlow {
        val firestore = FirebaseAppWithOptions.firestore
        val chatReference = firestore
            .collection("chatrooms")
            .doc(chatRoomId.rawValue)
            .get()
            .await()
            .let { document ->
                document.get("chat") as firebase.firestore.DocumentReference
            }
        val meUser = getMeService.get()

        val unsubscribe = chatReference
            .collection("chat")
            .orderBy("createdAt", "asc")
            .onSnapshot(next = { documentSnapshot ->
                async {


                    documentSnapshot.docs.map { document ->
                        val iineUsers =
                            (document.get("iineUsers") as Array<firebase.firestore.DocumentReference>)
                                .map {
                                    val userDocumentSnapshot = it.get().await()
                                    UserConverter.convertFromDocument(meUser?.id, userDocumentSnapshot)
                                }

                        val sendUser =
                            (document.get("sendUser") as firebase.firestore.DocumentReference)
                                .let {
                                    val userDocumentSnapshot = it.get().await()
                                    UserConverter.convertFromDocument(meUser?.id, userDocumentSnapshot)
                                }

                        ChatConverter.convertFromDocument(document, iineUsers, sendUser)
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