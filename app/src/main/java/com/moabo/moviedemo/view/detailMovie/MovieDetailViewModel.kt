package com.moabo.moviedemo.view.detailMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moabo.moviedemo.model.MovieDao
import com.moabo.moviedemo.model.movie.Movie
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.model.rating.RatingRQ
import com.moabo.moviedemo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val movieDao: MovieDao
) :
    ViewModel() {

    private val _movieDetailStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var movieDetailStateFlow: StateFlow<ApiState> = _movieDetailStateFlow

    private val _rateStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var rateStateFlow: StateFlow<ApiState> = _rateStateFlow

    fun getMovieDetails(movie: Movie, refreshApi: Boolean) = viewModelScope.launch {
        _movieDetailStateFlow.value = ApiState.Loading
        if (refreshApi) {
            movie.id?.let {id->
                mainRepository.getMovieDetail(id)
                    .catch {
                        _movieDetailStateFlow.value = ApiState.Failure(it)
                    }
                    .collect {
                        _movieDetailStateFlow.value = ApiState.Success(it)
                    }
            }
        } else {
            _movieDetailStateFlow.value = ApiState.Success(movie)
        }


    }

    fun rateMovie(value: RatingRQ, movieId: Int, guestSessionId: String) = viewModelScope.launch {

        _rateStateFlow.value = ApiState.Loading
        mainRepository.rateMovie(value, movieId, guestSessionId)
            .catch {
                _rateStateFlow.value = ApiState.Failure(it)
            }
            .collect {
                _rateStateFlow.value = ApiState.Success(it)
            }
    }

    fun addToFavorite(movie: Movie) = viewModelScope.launch {
        movieDao.updateMovie(movie)
    }


}