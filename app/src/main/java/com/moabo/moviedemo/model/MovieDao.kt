package com.moabo.moviedemo.model

import androidx.room.*
import com.moabo.moviedemo.model.movie.Movie


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movie LIMIT 20")
    suspend fun getMovies() : List<Movie>

    @Query("SELECT * FROM movie where isFavorite=1 ")
    suspend fun getFavoriteMovies() : List<Movie>

    @Update
    fun updateMovie(movie: Movie?)



}