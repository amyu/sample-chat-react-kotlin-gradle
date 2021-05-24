package ui.page.home.chat_room

import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.css
import styled.styledDiv
import kotlin.random.Random

fun RBuilder.CardComponent(
    backgroundColor: Color,
    onClickCard: () -> Unit,
    children: RBuilder.() -> Unit,
) {
    styledDiv {
        css {
            this.backgroundColor = backgroundColor
            overflow = Overflow.hidden
            borderRadius = 8.px
            boxShadow(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 8.px,
                spreadRadius = 0.px,
                color = rgba(0, 0, 0, 0.2)
            )
            position = Position.relative
            cursor = Cursor.pointer
            hover {
                opacity = 0.7
            }
        }
        attrs {
            onClickFunction = {
                onClickCard()
            }
        }

        children()
    }
}