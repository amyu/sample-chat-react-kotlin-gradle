package ui.page.chatroom.input

import domain.chat.input.InputEmptyValidator
import domain.exception.DomainException
import kotlinx.css.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onKeyPressFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import react.*
import react.dom.value
import styled.css
import styled.styledTextArea


interface InputMessageComponentProps : RProps {
    var onSendMessage: (String) -> Unit
}

private val InputMessageComponent = functionalComponent<InputMessageComponentProps> { props ->
    val onSendMessage = props.onSendMessage
    val (message, setMessage) = useState("")
    val handleChange = useCallback<Event.() -> Unit>(callback = {
        target.unsafeCast<HTMLInputElement>().value.let(setMessage)
    }, dependencies = arrayOf(message))
    val handleKeyPress = useCallback<Event.() -> Unit>(callback = {
        val keyboardEvent = this.unsafeCast<KeyboardEvent>()
        if (keyboardEvent.key == "Enter") {
            // Shiftが押されてるときはメッセージを送らない
            if (!keyboardEvent.shiftKey) {
                try {
                    InputEmptyValidator.check(message)
                    onSendMessage(message)

                    // https://stackoverflow.com/questions/40606080/clear-textarea-after-enter-key
                    // valueに空文字を入れても改行されたままだったため､↑で解決
                    keyboardEvent.preventDefault()

                    setMessage("")
                } catch (e: DomainException.InputValidateException) {
                    when (e) {
                        DomainException.InputValidateException.EmptyException -> {
                            // 入力値が空文字だったときの処理
                            // 何もしない
                        }
                        DomainException.InputValidateException.FourLetterWordException -> {
                            // 入力値に禁止ワードが含まれてたときの処理
                            // 何かしらエラーを表示する
                            // 実装の時間都合でなし
                        }
                    }
                }
            }
        }
    }, dependencies = arrayOf(message))

    styledTextArea(rows = "1") {
        css {
            // TODO ここ無理やりすぎるから治す
            width = 100.pct - 32.px
            backgroundColor = Color("#2C2D2E")
            borderRadius = 30.px
            color = Color.white
            put("resize", "none")
            fontSize = 14.px
            paddingTop = 8.px
            paddingBottom = 8.px
            paddingLeft = 16.px
            paddingRight = 16.px
            focus {
                put("outline", "none")
            }
        }
        attrs {
            value = message
            onKeyPressFunction = handleKeyPress
            onChangeFunction = handleChange
        }
    }
}

fun RBuilder.InputMessageComponent(
    handler: InputMessageComponentProps.() -> Unit
) = child(InputMessageComponent) {
    attrs { handler() }
}