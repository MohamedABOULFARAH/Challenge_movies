package com.moabo.moviedemo.network

import com.moabo.moviedemo.model.genre.GenreMap
import com.moabo.moviedemo.model.movie.MovieDetail
import com.moabo.moviedemo.model.rating.RatingRQ
import com.moabo.moviedemo.model.rating.RatingRS
import com.moabo.moviedemo.model.search.SearchMap
import com.moabo.moviedemo.model.session.SessionRS
import com.moabo.moviedemo.model.topRated.TopRatedMap
import retrofit2.http.*

interface ApiService {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreMap

    @GET("search/movie")
    suspend fun getSearch(@Query("query")query :String): SearchMap

    @GET("authentication/guest_session/new")
    suspend fun getSession(): SessionRS

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieId :Int): MovieDetail

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page")page :Int): TopRatedMap


    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(@Body value: RatingRQ, @Path("movie_id")movieId :Int, @Query("guest_session_id")guestSessionId :String):RatingRS

}