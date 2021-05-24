package infra.user

import domain.user.SignInAnonymouslyService
import infra.firebase.FirebaseAppWithOptions
import kotlinx.coroutines.await
import kotlin.js.json

class SignInAnonymouslyServiceImpl: SignInAnonymouslyService {
    override suspend fun signIn() {
        val authResult = FirebaseAppWithOptions.firebaseAuth.signInAnonymously().await()
        //todo あとでException定義する
        val uid = authResult.user?.uid!!
        FirebaseAppWithOptions.firestore
            .collection("users")
            .doc(uid)
            .set(
                json(
                    "name" to "01"
                )
            )
    }
}