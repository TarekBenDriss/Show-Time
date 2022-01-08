package com.hamdy.showtime.ui.ui.person_list.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hamdy.showtime.ui.model.PersonsResponse
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.network.PersonsService
import com.hamdy.showtime.ui.util.API_KEY
import com.hamdy.showtime.ui.util.FAVORITE_ID_KEY
import com.hamdy.showtime.ui.util.FAVORITE_PERSONS_KEY
import com.hamdy.showtime.ui.util.USERS_KEY
import kotlinx.coroutines.tasks.await

class PersonsRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG = "PersonsRepository"

    suspend fun getPopular(page: Int): PersonsResponse? {
        val client = RetrofitClient.getInstance().create(PersonsService::class.java)
            .getPopular(API_KEY, page)
        return client.body()
    }

    suspend fun getAllFavorite(): MutableMap<String, Boolean> {
        val collectionReference =
            db.collection(USERS_KEY).document(auth.currentUser?.uid.toString())
                .collection(FAVORITE_PERSONS_KEY)
        val myMap: MutableMap<String, Boolean> = HashMap()
        val result = collectionReference.get().await()
        for (i in result)
            myMap[i[FAVORITE_ID_KEY].toString()] = true
        return myMap
    }

}