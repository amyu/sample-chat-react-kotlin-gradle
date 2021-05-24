package domain.chat

import domain.Entity
import domain.user.User
import kotlin.random.Random


sealed class Chat(
    override val id: ChatId,
    open val sendUser: User,
) : Entity<ChatId>(id) {
    /**
     * 自分が送ったチャットかどうか
     * 基底Classに実装しているため､このChatを実装しているものはすべて使える
     */
    val isMeChat: Boolean
        get() = sendUser is User.Me

    data class Message(
        override val id: ChatId,
        override val sendUser: User,
        override val iineUsers: List<User>,
        val message: String,
    ) : Chat(id, sendUser), CanIine

    data class Image(
        override val id: ChatId,
        override val sendUser: User,
        override val iineUsers: List<User>,
        val imageUrl: String,
    ) : Chat(id, sendUser), CanIine

    /**
     * 将来Stampなどが来たときは以下のように定義を追加していく
     *
     * data class Stamp(
     *     override val id: ChatId,
     *     override val sendUser: User,
     *     val stampId: StampId
     * ): Chat(id, sendUser)
     */
}