package ui.error

import domain.exception.DomainException
import kotlinx.browser.window
import react.RBuilder


/**
 * このエラーハンドリングはうまく機能していない
 * なぜかわからない
 */
fun RBuilder.ErrorHandler(children: RBuilder.() -> Unit) {
    try {
        children()
    } catch (domainException: DomainException) {
        window.alert("error")
    }
}
