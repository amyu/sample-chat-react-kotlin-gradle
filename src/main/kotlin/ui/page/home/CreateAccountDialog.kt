package ui.page.home

import kotlinx.css.*
import kotlinx.css.properties.transform
import kotlinx.css.properties.translateY
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import styled.css
import styled.styledDiv

interface CreateAccountDialogProps : RProps {
    var onClickCancel: () -> Unit
    var onSuccessAddRoom: () -> Unit
}

private val CreateAccountDialog = functionalComponent<CreateAccountDialogProps> { props ->
    val onClickCancel = props.onClickCancel
    val onSuccessAddRoom = props.onSuccessAddRoom

    val handleClickAdd = useCallback<Event.() -> Unit>(callback = {
        onSuccessAddRoom()
    }, dependencies = arrayOf())

    useEffect {

    }

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
                height = 300.px
                backgroundColor = Color("#1B1C1D")
                marginLeft = LinearDimension.auto
                marginRight = LinearDimension.auto
                borderRadius = 16.px
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
                    marginTop = 32.px
                    flexGrow = 1.0
                }

                +"ルーム名: ほげほげ"
            }

            styledDiv {
                css {
                    marginRight = LinearDimension.auto
                    marginLeft = LinearDimension.auto
                    marginBottom = 32.px
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

fun RBuilder.CreateAccountDialog(handler: CreateAccountDialogProps.() -> Unit) = child(CreateAccountDialog) {
    attrs { handler() }
}