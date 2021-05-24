package infra.chat_room

import dev.gitlive.firebase.firebase
import domain.chat_room.ChatRoomId
import domain.chat_room.LeaveChatRoomService
import domain.user.GetMeService
import domain.user.UserId
import infra.firebase.FirebaseAppWithOptions
import infra.user.GetMeServiceImpl
import kotlinx.coroutines.await

class LeaveChatRoomServiceImpl: LeaveChatRoomService {
    // DIしたい...
    private val getMeService: GetMeService = GetMeServiceImpl()

    override suspend fun leave(chatRoomId: ChatRoomId) {
        //todo Exceptionを定義する
        val meUser = getMeService.get()!!

        val currentJoinUserIds = FirebaseAppWithOptions.firestore
            .collection("chatrooms")
            .doc(chatRoomId.rawValue)
            .get()
            .await()
            .let { document ->
                (document.get("joinedUsers") as Array<firebase.firestore.DocumentReference>)
                    .map {
                        UserId(it.id)
                    }
            }

        if (currentJoinUserIds.isEmpty()){
            return
        }

        val userReferences = (currentJoinUserIds - meUser.id).map {
            FirebaseAppWithOptions.firestore.doc("users/${it}")
        }.toTypedArray()

        FirebaseAppWithOptions.firestore
            .collection("chatrooms")
            .doc(chatRoomId.rawValue)
            .update("joinedUsers", userReferences)
            .await()
    }
}