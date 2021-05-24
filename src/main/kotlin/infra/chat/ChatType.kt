package infra.chat

/**
 * Infra層でしか使わない
 * package privateもしくはModuleを切りアクセス制限したい
 */
internal enum class ChatType(val raw: Int) {
    MESSAGE(0), IMAGE(1);

    companion object {
        fun fromRawValue(raw: Int): ChatType = values().first { it.raw == raw }
    }
}