package infra.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize

object FirebaseAppWithOptions {
    private val firebaseApp = Firebase.initialize(
        options = FirebaseOptions(
            applicationId = "1:115619226501:web:7d8fc6ff167d7293509aec",
            apiKey = "AIzaSyBHmD5ivEeNmpeyhIcu9kqESCOE6DGTTpA",
            storageBucket = "chat-79ed7.appspot.com",
            projectId = "chat-79ed7",
        )
    )

    val firestore: firebase.firestore.Firestore = Firebase.firestore(firebaseApp).js

    val firebaseAuth: firebase.auth.Auth = Firebase.auth(firebaseApp).js
}