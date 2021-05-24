package ui.page.home.chat_room

import domain.DomainContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.css.properties.transform
import kotlinx.css.properties.translateY
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import styled.css
import styled.styledDiv
import styled.styledInput

interface AddChatRoomDialogProps : RProps {
    var onClickCancel: () -> Unit
    var onSuccessAddRoom: () -> Unit
}

private val scope = MainScope()

private val AddChatRoomDialog = functionalComponent<AddChatRoomDialogProps> { props ->
    val onClickCancel = props.onClickCancel
    val onSuccessAddRoom = props.onSuccessAddRoom

    val domainContext = useContext(DomainContext)

    val (roomName, setRoomName) = useState("")
    val handleChange = useCallback<Event.() -> Unit>(callback = {
        setRoomName(target.unsafeCast<HTMLInputElement>().value)
    }, dependencies = arrayOf())
    val handleClickAdd = useCallback<Event.() -> Unit>(callback = {
        scope.launch {
            runCatching {
                domainContext.addChatRoomService
                    .add(roomName)
            }.onSuccess {
                onSuccessAddRoom()
            }.onFailure {
            }
        }
    }, dependencies = arrayOf(roomName))
    val handleClickCancel = useCallback<Event.() -> Unit>(callback = {
        onClickCancel()
    }, dependencies = arrayOf())


    styledDiv {
        css {
            width = 100.pct
            height = 100.vh
            position = Position.fixed
            backgroundColor = rgba(0, 0, 0, 0.5)
            top = 0.px
            left = 0.px
            right = 0.px
            left = 0.px
            overflow = Overflow.auto
        }

        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                width = 400.px
                backgroundColor = Color("#1B1C1D")
                marginLeft = LinearDimension.auto
                marginRight = LinearDimension.auto
                borderRadius = 16.px
                padding(32.px)
                transform {
                    // Dialogを中心に置きたかった...
                    // 時間があったら治す
                    translateY(50.vh - 100.pct)
                }
            }

            styledDiv {
                css {
                    marginRight = LinearDimension.auto
                    marginLeft = LinearDimension.auto
                    fontWeight = FontWeight.bold
                }

                +"ルーム名"
            }

            styledInput {
                css {
                    marginTop = 32.px
                    backgroundColor = Color("#2C2D2E")
                    borderRadius = 30.px
                    color = Color.white
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
                    value = roomName
                    onChangeFunction = handleChange
                }
            }

            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                styledDiv {
                    css {
                        marginTop = 32.px
                        marginRight = LinearDimension.auto
                        marginLeft = LinearDimension.auto
                        cursor = Cursor.pointer
                        hover {
                            opacity = 0.7
                        }
                    }
                    attrs {
                        this.onClickFunction = handleClickCancel
                    }
                    +"キャンセル"
                }
                styledDiv {
                    css {
                        marginTop = 32.px
                        marginRight = LinearDimension.auto
                        marginLeft = LinearDimension.auto
                        cursor = Cursor.pointer
                        hover {
                            opacity = 0.7
                        }
                    }
                    attrs {
                        this.onClickFunction = handleClickAdd
                    }
                    +"作成"
                }
            }

        }
    }
}

fun RBuilder.AddChatRoomDialog(handler: AddChatRoomDialogProps.() -> Unit) = child(AddChatRoomDialog) {
    attrs { handler() }
}