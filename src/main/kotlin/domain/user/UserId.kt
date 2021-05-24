package domain.user

import domain.Identifier

class UserId(value: String) : Identifier<String>(value) {
    companion object {
        // debug用
        fun generateRandomId(): UserId = UserId(generateRandomStringId())
    }
}