package domain.chat

import domain.user.User
import domain.user.UserId

/**
 * `いいね` をすることができるChatに実装
 * 例えば､将来的にスタンプ機能を追加するときなどに､スタンプには `いいね` ができなくてよい､
 * などのことを考慮しInterfaceへ切り出した
 *
 * if (chat is Caniine) {
 *   IineComponent()
 * }
 * のようにUIでハンドリングしても良い
 *
 * さらに `いいね` だけでなく複数のリアクションに対応したい場合などはこのInterfaceを変更し､対応する
 */
interface CanIine {
    val iineUsers: List<User>

    /**
     * 自分がいいねしたかどうか
     */
    fun isIined(meUserId: UserId): Boolean =
        iineUsers.map { it.id }.contains(meUserId)
}