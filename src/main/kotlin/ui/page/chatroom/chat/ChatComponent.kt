package ui.page.chatroom.chat

import domain.chat.Chat
import domain.chat.ChatId
import domain.user.User
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import kotlinx.css.properties.transform
import kotlinx.css.properties.translateY
import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.css
import styled.styledDiv
import styled.styledImg
import ui.component.UserIconComponent


fun RBuilder.ChatComponent(
    chat: Chat,
    onClickIine: (chatId: ChatId) -> Unit
) {
    styledDiv {
        when (chat) {
            is Chat.Message -> if (chat.isMeChat) {
                RightMessageComponent(chat, onClickIine)
            } else {
                LeftMessageComponent(chat, onClickIine)
            }
            is Chat.Image -> if (chat.isMeChat) {
                RightImageComponent(chat, onClickIine)
            } else {
                LeftImageComponent(chat, onClickIine)
            }
        }
    }
}

private fun RBuilder.LeftMessageComponent(
    chat: Chat.Message,
    onClickIine: (chatId: ChatId) -> Unit,
) {
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }

        styledDiv {
            css {
                width = 50.px
            }
            UserIconComponent(chat.sendUser)
        }

        styledDiv {
            styledDiv {
                css {
                    backgroundColor = Color("#2C2D2E")
                    borderRadius = 30.px
                    marginLeft = 8.px
                    maxWidth = 520.px
                    padding(16.px)
                }
                styledDiv {
                    css {
                        width = 100.pct
                        wordBreak = WordBreak.breakAll
                        whiteSpace = WhiteSpace.preWrap
                    }
                    +chat.message
                }
            }

            styledDiv {
                css {
                    width = LinearDimension.maxContent
                    marginLeft = LinearDimension.auto
                    marginRight = 16.px
                    transform {
                        translateY((-50).pct)
                    }
                }
                IineComponennt(chat.iineUsers) {
                    onClickIine(chat.id)
                }
            }
        }
    }
}

private fun RBuilder.RightMessageComponent(
    chat: Chat.Message,
    onClickIine: (chatId: ChatId) -> Unit,
) {
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }

        styledDiv {
            css {
                marginRight = 0.px
                marginLeft = LinearDimension.auto
            }
            styledDiv {
                css {
                    backgroundColor = Color("#246BFF")
                    borderRadius = 30.px
                    maxWidth = 520.px
                    padding(16.px)
                }
                styledDiv {
                    css {
                        width = 100.pct
                        wordBreak = WordBreak.breakAll
                        whiteSpace = WhiteSpace.preWrap
                    }
                    +chat.message
                }
            }

            styledDiv {
                css {
                    width = LinearDimension.maxContent
                    marginLeft = LinearDimension.auto
                    marginRight = 16.px
                    transform {
                        translateY((-50).pct)
                    }
                }
                IineComponennt(chat.iineUsers) {
                    onClickIine(chat.id)
                }
            }
        }
    }
}

private fun RBuilder.LeftImageComponent(
    chat: Chat.Image,
    onClickIine: (chatId: ChatId) -> Unit,
) {
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }

        styledDiv {
            css {
                width = 50.px
            }
            UserIconComponent(chat.sendUser)
        }

        styledDiv {
            styledDiv {
                css {
                    marginLeft = 8.px
                }
                ImageComponent(chat.imageUrl)
            }
            styledDiv {
                css {
                    width = LinearDimension.maxContent
                    marginLeft = LinearDimension.auto
                    marginRight = 16.px
                    transform {
                        translateY((-50).pct)
                    }
                }
                IineComponennt(chat.iineUsers) {
                    onClickIine(chat.id)
                }
            }
        }
    }
}

private fun RBuilder.RightImageComponent(
    chat: Chat.Image,
    onClickIine: (chatId: ChatId) -> Unit,
) {
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }

        styledDiv {
            css {
                marginRight = 0.px
                marginLeft = LinearDimension.auto
            }
            ImageComponent(chat.imageUrl)

            styledDiv {
                css {
                    width = LinearDimension.maxContent
                    marginLeft = LinearDimension.auto
                    marginRight = 16.px
                    transform {
                        translateY((-50).pct)
                    }
                }
                IineComponennt(chat.iineUsers) {
                    onClickIine(chat.id)
                }
            }
        }

    }
}

private fun RBuilder.ImageComponent(url: String) {
    styledImg(src = url) {
        css {
            width = 520.px
            borderRadius = 16.px
        }
    }
}

private fun RBuilder.IineComponennt(
    iineUsers: List<User>,
    onClickIine: () -> Unit,
) {
    styledDiv {
        css {
            width = LinearDimension.maxContent
            backgroundColor = Color("#2C2D2E")
            borderRadius = 8.px
            textAlign = TextAlign.center
            cursor = Cursor.pointer
            overflow = Overflow.hidden
            fontSize = 12.px

            boxShadow(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 8.px,
                spreadRadius = 0.px,
                color = rgba(0, 0, 0, 0.2)
            )
            padding(8.px)
            hover {
                opacity = 0.7
            }
        }

        attrs {
            onClickFunction = {
                onClickIine()
            }
        }
        +"\uD83D\uDC4D ${iineUsers.count()}"
    }
}
