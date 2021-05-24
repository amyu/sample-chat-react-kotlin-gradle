package infra.user

import dev.gitlive.firebase.firebase
import domain.user.User
import domain.user.UserId

object UserConverter {
    fun convertFromDocument(
        myUserId: UserId?,
        documentSnapshot: firebase.firestore.DocumentSnapshot
    ): User {
        val userId = UserId(documentSnapshot.id)

        return if (userId == myUserId) {
            User.Me(
                id = userId,
                name = documentSnapshot.get("name") as String,
            )
        } else {
            User.Other(
                id = userId,
                name = documentSnapshot.get("name") as String,
            )
        }
    }
}
