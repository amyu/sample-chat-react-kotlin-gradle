package ui.page.home

import domain.DomainContext
import domain.chat_room.ChatRoom
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import react.*
import react.dom.h1
import ui.page.home.chat_room.AddChatRoomDialog
import ui.page.home.chat_room.ChatRoomListLayout

private val scope = MainScope()

private val HomePage = functionalComponent<RProps> {
    val domainContext = useContext(DomainContext)

    val (chatRooms, setChatRooms) = useState<List<ChatRoom>>(emptyList())
    val (isVisibleAddChatRoomDialog, setIsVisibleAddChatRoomDialog) = useState(false)
    val (isVisibleCreateAccountDialog, setIsVisibleCreateAccountDialog) = useState(false)

    val handleClickAddChatRoom = useCallback(callback = {
        setIsVisibleAddChatRoomDialog(true)
    }, dependencies = arrayOf(chatRooms))
    val handleCancelAddRoom = useCallback(callback = {
        setIsVisibleAddChatRoomDialog(false)
    }, dependencies = arrayOf(isVisibleAddChatRoomDialog))
    val handleSuccessAddRoom = useCallback(callback = {
        setIsVisibleAddChatRoomDialog(false)
    }, dependencies = arrayOf(isVisibleAddChatRoomDialog))

    useEffect(dependencies = listOf()) {
        scope.launch {
            domainContext.observeChatRoomListService
                .observe()
                .collect {
                    setChatRooms(it)
                }
        }
    }

    h1 {
        +"Chat Room List"
    }
    ChatRoomListLayout(
        chatRooms,
        onClickAddChatRoom = handleClickAddChatRoom
    )

    if (isVisibleAddChatRoomDialog) {
        AddChatRoomDialog {
            onClickCancel = handleCancelAddRoom
            onSuccessAddRoom = handleSuccessAddRoom
        }
    }
    if (isVisibleCreateAccountDialog) {
        CreateAccountDialog {

        }
    }
}

fun RBuilder.HomePage() = child(HomePage)