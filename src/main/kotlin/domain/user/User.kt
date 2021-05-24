package domain.user

import domain.Entity
import kotlin.random.Random


/**
 * todo ちょっと考える 拡張性がうすすぎる
 * チャットを行うだけのサービスならこれで問題ないが､明らかに考慮の足りないModeling...
 */
sealed class User(
    override val id: UserId,
    open val name: String
) : Entity<UserId>(id) {
    data class Me(
        override val id: UserId,
        override val name: String
    ) : User(id, name)

    data class Other(
        override val id: UserId,
        override val name: String
    ) : User(id, name)

    companion object {
        // debug用
        fun generateRandomUser(): User = if (Random.nextBoolean()) {
            Me(
                id = UserId.generateRandomId(),
                name = ('A'..'Z').map { it }.shuffled().subList(0, 4).joinToString(""),
            )
        } else {
            Other(
                id = UserId.generateRandomId(),
                name = ('A'..'Z').map { it }.shuffled().subList(0, 4).joinToString(""),
            )
        }

    }
}