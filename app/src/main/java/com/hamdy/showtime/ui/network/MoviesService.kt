package com.hamdy.showtime.ui.network

import com.hamdy.showtime.ui.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(POPULAR_MOVIES)
    suspend fun getPopular(
        @Query(API_KEY) key: String,
        @Query(PAGE) page: Int
    ): Response<PopularResponse>

    @GET(TOP_RATED_MOVIES)
    suspend fun getTopRated(
        @Query(API_KEY) key: String,
        @Query(PAGE) page: Int
    ): Response<PopularResponse>

    @GET(UPCOMING_MOVIES)
    suspend fun getUpComing(
        @Query(API_KEY) key: String,
        @Query(PAGE) page: Int
    ): Response<PopularResponse>

    @GET(TRENDING_MOVIES)
    suspend fun getTrending(
        @Query(API_KEY) key: String,
        @Query(PAGE) page: Int
    ): Response<PopularResponse>

    @GET(MOVIE_CAST)
    suspend fun getCastMovieList(
        @Path(ID) page: Int,
        @Query(API_KEY) key: String
    ): Response<CastResponse>

    @GET(MOVIE_TRAILER)
    suspend fun getTrailer(
        @Path(ID) id: Int,
        @Query(API_KEY) key: String
    ): Response<VideoResponse>

    @GET(MOVIE_DETAILS)
    suspend fun getMoviesDetails(
        @Path(ID) page: Int,
        @Query(API_KEY) key: String
    ): Response<MoviesDetailsResponse>

    @GET(SEARCH_MOVIES)
    suspend fun getMoviesSearch(
        @Query(API_KEY) key: String,
        @Query(QUERY) query: String
    ): Response<SearchMoviesResponse>

    @GET(SEARCH_PERSONS)
    suspend fun getPersonsSearch(
        @Query(API_KEY) key: String,
        @Query(QUERY) query: String
    ): Response<SearchPersonResponse>

    companion object {
        /** URIs **/
        const val POPULAR_MOVIES = "movie/popular?"
        const val TOP_RATED_MOVIES = "movie/top_rated?"
        const val UPCOMING_MOVIES = "movie/upcoming?"
        const val TRENDING_MOVIES = "trending/movie/day?"
        const val MOVIE_CAST = "movie/{id}/credits?"
        const val MOVIE_TRAILER = "movie/{id}/videos?"
        const val MOVIE_DETAILS = "movie/{id}?"
        const val SEARCH_MOVIES = "search/movie?"
        const val SEARCH_PERSONS = "search/person?"

        /** KEYS **/
        const val API_KEY = "api_key"
        const val QUERY = "query"
        const val ID = "id"
        const val PAGE = "page"
    }

}