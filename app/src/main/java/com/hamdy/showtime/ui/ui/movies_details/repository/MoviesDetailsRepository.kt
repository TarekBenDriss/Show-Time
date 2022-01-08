package com.hamdy.showtime.ui.ui.movies_details.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hamdy.showtime.ui.model.*
import com.hamdy.showtime.ui.network.MoviesService
import com.hamdy.showtime.ui.network.RetrofitClient
import com.hamdy.showtime.ui.util.*
import kotlinx.coroutines.tasks.await

class MoviesDetailsRepository {
    private val TAG = "MoviesDetailsRepository"
    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun getCastMovieList(id: Int): List<CastItem?>? {
        val client = RetrofitClient.getInstance().create(MoviesService::class.java)
            .getCastMovieList(id, API_KEY)
        return client.body()?.cast
    }

    suspend fun getMoviesDetails(id: Int): MoviesDetailsResponse? {
        val client = RetrofitClient.getInstance().create(MoviesService::class.java)
            .getMoviesDetails(id, API_KEY)
        client.isSuccessful
        return client.body()
    }

    suspend fun getFavorite(id: Int): Boolean {
        val collectionReference =
            db.collection(USERS_KEY).document(auth.currentUser?.uid.toString())
                .collection(FAVORITE_MOVIES_KEY).document(id.toString())
        val result = collectionReference.get().await()
        return result.exists()
    }

    suspend fun getTrailer(id: Int): ResultsVideoItem? {
        val client =
            RetrofitClient.getInstance().create(MoviesService::class.java).getTrailer(id, API_KEY)
        var resultsVideoItem: ResultsVideoItem? = null
        if (client.body()?.results != null) {
            for (i in client.body()?.results!!) {
                if (i?.type == TRAILER_KEY) {
                    resultsVideoItem = i
                    break
                } else {
                    resultsVideoItem = i
                }
            }
        }
        return resultsVideoItem
    }


    suspend fun setFavorite(id: Int, poster: String, exist: Boolean) {
        if (!exist) {
            val collectionReference =
                db.collection(USERS_KEY).document(auth.currentUser?.uid.toString())
                    .collection(FAVORITE_MOVIES_KEY).document(id.toString())
            val map = HashMap<String, String>()
            map[POSTER_KEY] = poster
            map[FAVORITE_ID_KEY] = id.toString()
            collectionReference.set(map).await()
        } else {
            val collectionReference =
                db.collection(USERS_KEY).document(auth.currentUser?.uid.toString())
                    .collection(FAVORITE_MOVIES_KEY).document(id.toString())
            collectionReference.delete().await()
        }
    }

}