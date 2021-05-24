package ui.page.chatroom

import domain.DomainContext
import domain.chat.Chat
import domain.chat.ChatId
import domain.chat_room.ChatRoomId
import domain.exception.DomainException
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.css.*
import org.w3c.dom.Element
import org.w3c.files.File
import react.*
import react.dom.div
import react.dom.h1
import react.router.dom.useHistory
import styled.css
import styled.styledDiv
import ui.page.chatroom.chat.ChatListLayout
import ui.page.chatroom.input.CHAT_INPUT_HEIGHT
import ui.page.chatroom.input.ChatInputComponent
import kotlin.js.json

interface ChatRoomPageProps : RProps {
    val chatRoomId: String
}

interface InternalChatRoomPageProps : RProps {
    var chatRoomId: ChatRoomId
}

private val scope = MainScope()

private val ChatRoomPage = functionalComponent<InternalChatRoomPageProps> { props ->
    val chatRoomId = props.chatRoomId
    val (chats, setChats) = useState<List<Chat>>(emptyList())

    val domainContext = useContext(DomainContext)
    val history = useHistory()

    val chatsEndRef = createRef<Element>()

    val handleSelectedImageFile = useCallback<File.() -> Unit>(callback = {
        scope.launch {
            domainContext.uploadImageService.upload(chatRoomId, this@useCallback)
        }
    }, dependencies = arrayOf())
    val handleSendMessage = useCallback<String.() -> Unit>(callback = {
        scope.launch {
            domainContext.sendMessageService
                .send(chatRoomId, this@useCallback)
        }
    }, dependencies = arrayOf())
    val handleClickIine = useCallback<ChatId.() -> Unit>(callback = {
        scope.launch {
            // ここのChatする処理イケてない..
            // UIからChatを返すか､Domain以下でよしなにやってほしい
            val chat = chats.find { it.id == this@useCallback } ?: return@launch

            // いいねをした後の反映が若干長いため､UIに一旦反映させるなどの処理が必要そう
            domainContext.toggleIineService
                .toggle(chatRoomId, chat)
        }
    }, dependencies = arrayOf(chats))

    val scrollToBottom = {
        chatsEndRef.current?.scrollIntoView(
            json(
                "behavior" to "smooth",
            )
        )
    }

    useEffectWithCleanup(dependencies = emptyList()) {
        val jobs: MutableList<Job> = mutableListOf()

        scope.launch {
            domainContext.observeMeService
                .observe()
                .collect {
                    // MeがNullということはログアウトした状態のためTopへ遷移する
                    if (it == null) {
                        history.push("/")
                    }
                }
        }.let {
            jobs.add(it)
        }

        scope.launch {
            domainContext.joinChatRoomService
                .join(chatRoomId)

            /*
            Joinが完了したら部屋の状態を監視し､別タブなどでLeaveしたら
            Topへ遷移する
             */
            domainContext.observeChatRoomService
                .observe(chatRoomId)
                .collect {
                    val me = domainContext.getMeService.get() ?: return@collect
                    val isJoined = it.joinedUsers.any { it.id == me.id }
                    if (!isJoined) {
                        history.push("/")
                    }
                }
        }.let {
            jobs.add(it)
        }

        scope.launch {
            domainContext.observeChatService
                .observe(chatRoomId)
                .collect {
                    setChats(it)
                }
        }.let {
            jobs.add(it)
        }

        return@useEffectWithCleanup {
            jobs.forEach { it.cancel() }
            scope.launch {
                domainContext.leaveChatRoomService
                    .leave(chatRoomId)
            }
        }
    }

    useEffect(dependencies = listOf(chats)) {
        scrollToBottom()
    }

    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            height = 100.vh
        }

        h1 {
            +"Chat Room ${chatRoomId.rawValue}"
        }

        styledDiv {
            css {
                flexGrow = 1.0
                paddingBottom = CHAT_INPUT_HEIGHT + 16.px
            }
            ChatListLayout(chats, handleClickIine)
        }


        styledDiv {
            css {
                position = Position.fixed
                bottom = 0.px
                width = 100.pct
            }
            ChatInputComponent {
                this.chatRoomId = chatRoomId
                this.onSelectedImageFile = handleSelectedImageFile
                this.onSendMessage = handleSendMessage
            }
        }

        div {
            attrs {
                ref = chatsEndRef
            }
        }
    }
}

fun RBuilder.ChatRoomPage(handler: InternalChatRoomPageProps.() -> Unit) = child(ChatRoomPage) {
    attrs { handler() }
}