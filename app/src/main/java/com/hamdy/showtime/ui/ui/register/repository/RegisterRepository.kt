package com.hamdy.showtime.ui.ui.register.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.hamdy.showtime.ui.util.*
import kotlinx.coroutines.tasks.await

class RegisterRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun register(name: String, email: String, password: String): String {

        val result =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        return if (result.user != null) {
            val collectionReference = db.collection(USERS_KEY).document(result.user!!.uid)
            val map = HashMap<String, String>()
            map[USERNAME_KEY] = name
            map[USER_ID_KEY] = result.user!!.uid
            collectionReference.set(map).await()
            SUCCESSFUL_RESPONSE_VALUE
        } else {
            FAILED_RESPONSE_VALUE
        }
    }
}