package com.moabo.moviedemo.network

import com.moabo.moviedemo.model.genre.GenreMap
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.model.movie.MovieDetail
import com.moabo.moviedemo.model.rating.RatingRQ
import com.moabo.moviedemo.model.rating.RatingRS
import com.moabo.moviedemo.model.search.SearchMap
import com.moabo.moviedemo.model.session.SessionRS
import com.moabo.moviedemo.model.topRated.TopRatedMap
import org.json.JSONObject
import retrofit2.http.Body
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {
    suspend fun getGenres(): GenreMap = apiService.getGenres()
    suspend fun getSession(): SessionRS = apiService.getSession()
    suspend fun getSearch(query :String): SearchMap = apiService.getSearch(query)
    suspend fun getTopRatedMovies(page :Int): TopRatedMap = apiService.getTopRatedMovies(page)
    suspend fun getMovieDetail(movieId :Int): Movie = apiService.getMovieDetail(movieId)
    suspend fun rateMovie(value :RatingRQ,movieId :Int,guestSessionId :String): RatingRS = apiService.rateMovie(value,movieId,guestSessionId)
}