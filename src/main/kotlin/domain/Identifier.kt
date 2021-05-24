package domain

abstract class Identifier<T>(val rawValue: T) {
    override fun equals(other: Any?): Boolean = other is Identifier<*> && other.rawValue == rawValue

    override fun hashCode(): Int = rawValue.hashCode()

    override fun toString(): String = rawValue.toString()

    companion object {
        // debug用
        fun generateRandomStringId(): String = ('A'..'z').map { it }.shuffled().subList(0, 4).joinToString("")
    }
}