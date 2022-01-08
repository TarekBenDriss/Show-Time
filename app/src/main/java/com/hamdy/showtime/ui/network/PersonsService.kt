package com.hamdy.showtime.ui.network

import com.hamdy.showtime.ui.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonsService {

    @GET(POPULAR_PERSONS)
    suspend fun getPopular(@Query(API_KEY) key:String,@Query(PAGE) page:Int): Response<PersonsResponse>

    @GET(PERSON_DETAILS)
    suspend fun getPersonDetails(@Path(ID) page:Int,@Query(API_KEY) key:String): Response<PersonDetailsResponse>

    @GET(PERSON_IMAGE)
    suspend fun getPersonImage(@Path(ID) page:Int,@Query(API_KEY) key:String): Response<PersonImageResponse>

    @GET(PERSON_KNOWN_MOVIES)
    suspend fun getPersonKnownMovies(@Path(ID) page: String?, @Query(API_KEY) key:String,
                                     @Query(EXTERNAL_SOURCE) external_source:String): Response<FindPersonResponse>

    @GET(SEARCH_PERSON)
    suspend fun getPersonsSearch(@Query(API_KEY) key:String,@Query(QUERY) query:String): Response<SearchPersonResponse>

    companion object {
        /** URIs **/
        const val POPULAR_PERSONS = "person/popular?"
        const val PERSON_DETAILS = "person/{id}?"
        const val PERSON_IMAGE = "person/{id}/images?"
        const val PERSON_KNOWN_MOVIES = "find/{id}?"
        const val SEARCH_PERSON = "search/person?"

        /** KEYS **/
        const val API_KEY = "api_key"
        const val QUERY = "query"
        const val ID = "id"
        const val PAGE = "page"
        const val EXTERNAL_SOURCE = "external_source"
    }
}