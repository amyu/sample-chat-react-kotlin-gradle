package ui.page.chatroom.chat

import domain.chat.Chat
import domain.chat.ChatId
import kotlinx.css.*
import react.RBuilder
import react.dom.li
import styled.css
import styled.styledDiv
import styled.styledUl


fun RBuilder.ChatListLayout(
    chats: List<Chat>,
    onClickIine: (ChatId) -> Unit
) {
    styledDiv {
        css {
            height = 100.pct
        }

        styledUl {
            css {
                listStyleType = ListStyleType.none
                paddingLeft = 0.px
            }
            chats.forEach { chat ->
                li {
                    attrs {
                        key = chat.id.rawValue
                    }
                    ChatComponent(chat, onClickIine)
                }
            }
        }
    }
}
