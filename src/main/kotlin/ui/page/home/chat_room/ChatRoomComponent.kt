package ui.page.home.chat_room

import domain.DomainContext
import domain.chat_room.ChatRoom
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import react.*
import react.router.dom.useHistory
import styled.css
import styled.styledDiv
import styled.styledLi
import styled.styledUl
import ui.component.UserIconComponent

private val scope = MainScope()

interface ChatRoomComponentProps : RProps {
    var chatRoom: ChatRoom
}

private val ChatRoomComponent = functionalComponent<ChatRoomComponentProps> { props ->
    val domainContext = useContext(DomainContext)

    val chatRoom = props.chatRoom
    val history = useHistory()
    val handleClickCard = useCallback(callback = {
        scope.launch {
            val me = domainContext.getMeService.get()
            if (me == null) {
                domainContext.signInAnonymouslyService.signIn()
            }

            history.push("/chatroom/${chatRoom.id.rawValue}")
        }
    }, dependencies = emptyArray())

    CardComponent(
        backgroundColor = Color(chatRoom.roomColor),
        onClickCard = {
            handleClickCard()
        },
    ) {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                height = 100.pct
            }

            styledDiv {
                css {
                    margin(12.px)
                    fontWeight = FontWeight.bold
                    wordBreak = WordBreak.breakAll
                    flexGrow = 1.0
                }

                +chatRoom.roomName
            }

            styledUl {
                css {
                    overflowX = Overflow.auto
                    whiteSpace = WhiteSpace.nowrap
                    listStyleType = ListStyleType.none
                    paddingLeft = 8.px
                    paddingRight = 8.px
                    marginTop = LinearDimension.auto
                }
                chatRoom.joinedUsers.forEach { user ->
                    styledLi {
                        css {
                            display = Display.inlineBlock
                            marginLeft = 4.px
                            marginRight = 4.px
                        }
                        attrs {
                            key = user.id.rawValue
                        }
                        UserIconComponent(user)
                    }
                }
            }
        }
    }
}

fun RBuilder.ChatRoomComponent(handler: ChatRoomComponentProps.() -> Unit) = child(ChatRoomComponent) {
    attrs { handler() }
}
