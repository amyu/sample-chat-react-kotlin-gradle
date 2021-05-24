package domain

import domain.chat.ObserveChatService
import domain.chat.SendMessageService
import domain.chat.ToggleIinneService
import domain.chat.UploadImageService
import domain.chat_room.*
import domain.user.GetMeService
import domain.user.ObserveMeService
import domain.user.SignInAnonymouslyService
import infra.chat.ObserveChatServiceImpl
import infra.chat.SendMessageServiceImpl
import infra.chat.ToggleIinneServiceImpl
import infra.chat.UploadImageServiceImpl
import infra.chat_room.*
import infra.user.GetMeServiceImpl
import infra.user.ObserveMeServiceImpl
import infra.user.SignInAnonymouslyServiceImpl
import react.createContext


val DomainContext = createContext(defaultValue = DomainMap())

class DomainMap {
    /**
     * useContextをDI コンテナのように使いたかった
     * 現状のコードだと domain -> infra のように依存してしまっており､本当は良くない
     * きちんとしたDI コンテナを選別する時間が取れなかったため､一旦このような実装にしている
     *
     * ui -> domain <- infra
     * を目指していきたいところ
     */

    val addChatRoomService: AddChatRoomService = AddChatRoomServiceImpl()
    val observeChatRoomListService: ObserveChatRoomListService = ObserveChatRoomListServiceImpl()
    val joinChatRoomService: JoinChatRoomService = JoinChatRoomServiceImpl()
    val leaveChatRoomService: LeaveChatRoomService = LeaveChatRoomServiceImpl()
    val observeChatRoomService: ObserveChatRoomService = ObserveChatRoomServiceImpl()

    val getMeService: GetMeService = GetMeServiceImpl()
    val observeMeService: ObserveMeService = ObserveMeServiceImpl()
    val signInAnonymouslyService: SignInAnonymouslyService = SignInAnonymouslyServiceImpl()

    val observeChatService: ObserveChatService = ObserveChatServiceImpl()
    val sendMessageService: SendMessageService = SendMessageServiceImpl()
    val toggleIineService: ToggleIinneService = ToggleIinneServiceImpl()
    val uploadImageService: UploadImageService = UploadImageServiceImpl()
}