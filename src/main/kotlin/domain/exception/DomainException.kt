package domain.exception

sealed class DomainException: Throwable() {
    sealed class InputValidateException : DomainException() {
        // 入力値が空文字
        object EmptyException : InputValidateException()

        // 禁止文字
        object FourLetterWordException : InputValidateException()
    }
}