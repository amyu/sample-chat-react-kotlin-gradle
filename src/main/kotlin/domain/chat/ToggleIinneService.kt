package domain.chat

import domain.chat_room.ChatRoomId

/**
 * チャットへのいいねをToggleで行う
 *
 * Toggle式にするかどうかすごいなやんだ
 * Firestoreを使ってつねにデータを監視している点を考えてここではToggleで良いだろうと判断した
 */
interface ToggleIinneService {
    suspend fun toggle(
        chatRoomId: ChatRoomId,
        chat: Chat
    )
}