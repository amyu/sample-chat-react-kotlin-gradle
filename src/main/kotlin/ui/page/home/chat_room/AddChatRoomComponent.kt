package ui.page.home.chat_room

import kotlinx.css.*
import react.RBuilder
import ui.component.AddIcon
import kotlin.random.Random


fun RBuilder.AddChatRoomComponent(
    onClickAddChatRoom: () -> Unit
) {
    CardComponent(
        backgroundColor = rgba(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), 0.6),
        onClickCard = onClickAddChatRoom
    ) {
        AddIcon {
            width = 100.px
            height = 100.px
            margin(LinearDimension.auto)
            position = Position.absolute
            top = 0.px
            left = 0.px
            right = 0.px
            bottom = 0.px
        }
    }
}