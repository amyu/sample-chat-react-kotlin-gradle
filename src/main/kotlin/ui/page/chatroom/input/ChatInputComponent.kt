package ui.page.chatroom.input

import domain.chat_room.ChatRoomId
import kotlinx.css.*
import org.w3c.files.File
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import styled.css
import styled.styledDiv

val CHAT_INPUT_HEIGHT = 40.px

interface ChatInputComponentProps : RProps {
    var chatRoomId: ChatRoomId
    var onSelectedImageFile: (File) -> Unit
    var onSendMessage: (String) -> Unit
}

private val ChatInputComponent = functionalComponent<ChatInputComponentProps> { props ->
    val chatRoomId = props.chatRoomId
    val onSelectedImageFile = props.onSelectedImageFile
    val onSendMessage = props.onSendMessage
    styledDiv {
        css {
            height = CHAT_INPUT_HEIGHT
            display = Display.flex
            flexDirection = FlexDirection.row
            padding(16.px)
        }

        InputFileComponent(
            onChangeFile = onSelectedImageFile
        )

        styledDiv {
            css {
                flexGrow = 1.0
                marginLeft = 16.px
            }

            InputMessageComponent {
                this.onSendMessage = onSendMessage
            }
        }
    }
}

fun RBuilder.ChatInputComponent(handler: ChatInputComponentProps.() -> Unit) = child(ChatInputComponent) {
    attrs { handler() }
}

