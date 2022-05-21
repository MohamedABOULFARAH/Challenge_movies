package com.moabo.moviedemo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moabo.moviedemo.model.movie.Movie
import java.util.concurrent.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM movie LIMIT 10")
    suspend fun getMovies() : List<Movie>

}