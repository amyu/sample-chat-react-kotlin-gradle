package domain

abstract class Entity<T : Identifier<*>>(open val id: T) {
    override fun equals(other: Any?): Boolean = other is Entity<*> && other.id == id

    override fun hashCode(): Int = id.hashCode()
}