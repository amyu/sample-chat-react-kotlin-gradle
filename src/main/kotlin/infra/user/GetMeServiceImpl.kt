package infra.user

import domain.user.GetMeService
import domain.user.User
import domain.user.UserId
import infra.firebase.FirebaseAppWithOptions

class GetMeServiceImpl : GetMeService {
    override suspend fun get(): User.Me? =
        FirebaseAppWithOptions.firebaseAuth.currentUser?.let { user ->
            User.Me(
                id = UserId(user.uid),
                name = user.displayName ?: "01"
            )
        }
}