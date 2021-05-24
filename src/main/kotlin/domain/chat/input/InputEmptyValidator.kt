package domain.chat.input

import domain.exception.DomainException

object InputEmptyValidator  {
     fun check(message: String) {
         if (message.isBlank()) {
             throw DomainException.InputValidateException.EmptyException
         }
    }
}