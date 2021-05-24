package infra.user

import domain.user.ObserveMeService
import domain.user.User
import domain.user.UserId
import infra.firebase.FirebaseAppWithOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ObserveMeServiceImpl: ObserveMeService {
    override fun observe(): Flow<User.Me?> = callbackFlow {
        val unsubscribe = FirebaseAppWithOptions.firebaseAuth.onAuthStateChanged { user ->
            user?.let {
                User.Me(
                    id = UserId(user.uid),
                    name = user.displayName ?: "01"
                )
            }.let { offer(it) }
        }

        awaitClose {
            unsubscribe()
        }
    }
}