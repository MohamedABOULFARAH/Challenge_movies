package com.moabo.moviedemo.repository

import com.moabo.moviedemo.model.genre.GenreMap
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.model.movie.MovieDetail
import com.moabo.moviedemo.model.rating.RatingRQ
import com.moabo.moviedemo.model.rating.RatingRS
import com.moabo.moviedemo.model.search.SearchMap
import com.moabo.moviedemo.model.session.SessionRS
import com.moabo.moviedemo.model.topRated.TopRatedMap
import com.moabo.moviedemo.network.ApiServiceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {
    fun getGenres():Flow<GenreMap> = flow { emit(apiServiceImpl.getGenres()) }
    fun getSession():Flow<SessionRS> = flow { emit(apiServiceImpl.getSession()) }
    fun getSearch(query :String):Flow<SearchMap> = flow { emit(apiServiceImpl.getSearch(query)) }
    fun getTopRatedMovies(page :Int):Flow<TopRatedMap> = flow { emit(apiServiceImpl.getTopRatedMovies(page)) }
    fun getMovieDetail(movieId :Int):Flow<Movie> = flow { emit(apiServiceImpl.getMovieDetail(movieId)) }
    fun rateMovie(value :RatingRQ,movieId :Int,guestSessionId :String):Flow<RatingRS> = flow { emit(apiServiceImpl.rateMovie(value,movieId,guestSessionId)) }

}