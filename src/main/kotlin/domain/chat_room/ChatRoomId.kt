package domain.chat_room

import domain.Identifier

class ChatRoomId(value: String) : Identifier<String>(value) {
    companion object {
        // debug用
        fun generateRandomId(): ChatRoomId = ChatRoomId(generateRandomStringId())
    }
}

