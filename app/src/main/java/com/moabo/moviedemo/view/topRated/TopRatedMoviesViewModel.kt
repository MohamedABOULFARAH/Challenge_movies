package com.moabo.moviedemo.view.topRated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.model.MovieDao
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val movieDao: MovieDao,
    ) : ViewModel() {

    private val _topRatedResultStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var topRatedResultStateFlow: StateFlow<ApiState> = _topRatedResultStateFlow

    fun getTopRatedResults(page :Int) = viewModelScope.launch {

        _topRatedResultStateFlow.value = ApiState.Loading
        if (movieDao.getMovies().isNotEmpty()){
            getMovies()
        }else{
            mainRepository.getTopRatedMovies(page)
                .catch {
                    _topRatedResultStateFlow.value = ApiState.Failure(it)
                }
                .collect {
                    //_topRatedResultStateFlow.value = ApiState.Success(it.results)
                    insertMovie(it.results)
                }
        }

    }

    private fun insertMovie(movies: ArrayList<Movie>) = viewModelScope.launch {
       movies.forEach {
           movieDao.insert(it)
       }
        getMovies()
    }

    private fun getMovies() = viewModelScope.launch {
        _topRatedResultStateFlow.value = ApiState.Success(movieDao.getMovies())
    }
}