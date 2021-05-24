package ui.page.home.chat_room

import domain.chat_room.ChatRoom
import kotlinx.css.*
import react.RBuilder
import styled.css
import styled.styledDiv


fun RBuilder.ChatRoomListLayout(
    chatRooms: List<ChatRoom>,
    onClickAddChatRoom: () -> Unit
) {
    styledDiv {
        css {
            val gridItemWidth = 300.px
            val gridItemHeight = 200.px
            display = Display.grid
            gridTemplateColumns = GridTemplateColumns.repeat("auto-fit, ${gridItemWidth.value}")
            gridAutoRows = GridAutoRows(gridItemHeight)
            justifyContent = JustifyContent.center
            //todo 100.px.gap でいけるはずなのに拡張関数が表示されない
            gap = Gap(16.px.value)
        }
        chatRooms.forEach { chatRoom ->
            ChatRoomComponent {
                this.chatRoom = chatRoom
            }
        }
        AddChatRoomComponent(onClickAddChatRoom = {
            onClickAddChatRoom()
        })
    }
}
