package domain.chat

import domain.chat_room.ChatRoomId
import org.w3c.files.File

interface UploadImageService {
    suspend fun upload(
        chatRoomId: ChatRoomId,
        file: File,
    )
}