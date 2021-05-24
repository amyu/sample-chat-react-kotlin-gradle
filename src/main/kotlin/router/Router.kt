package router

import domain.chat_room.ChatRoomId
import react.Fragment
import react.RBuilder
import react.child
import react.router.dom.*
import ui.page.chatroom.ChatRoomPage
import ui.page.chatroom.ChatRoomPageProps
import ui.page.home.HomePage
import ui.page.not_found.NoFoundPage

fun RBuilder.Router() {
    hashRouter {
        switch {
            route("/", exact = true, strict = true) {
                HomePage()
            }
            route<ChatRoomPageProps>("/chatroom/:chatRoomId") {
                // ほんとはここらへんうまくやりたい
                ChatRoomPage {
                    this.chatRoomId = ChatRoomId(it.match.params.chatRoomId)
                }
            }
            route("/404") {
                Fragment { NoFoundPage() }
            }
            redirect(from = "", to = "/404")
        }
    }
}
