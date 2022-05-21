package com.moabo.moviedemo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.Util.ApiState
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
class MovieDetailViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _movieDetailStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var movieDetailStateFlow: StateFlow<ApiState> = _movieDetailStateFlow

    private val _rateStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var rateStateFlow: StateFlow<ApiState> = _rateStateFlow

    fun getMovieDetails(movieId :Int) = viewModelScope.launch {

        _movieDetailStateFlow.value = ApiState.Loading
        mainRepository.getMovieDetail(movieId)
            .catch {
                _movieDetailStateFlow.value = ApiState.Failure(it)
            }
            .collect {
                _movieDetailStateFlow.value = ApiState.Success(it)
            }
    }

    fun rateMovie(value:RatingRQ,movieId :Int,guestSessionId:String) = viewModelScope.launch {

        _rateStateFlow.value = ApiState.Loading
        mainRepository.rateMovie(value,movieId,guestSessionId)
            .catch {
                _rateStateFlow.value = ApiState.Failure(it)
            }
            .collect {
                _rateStateFlow.value = ApiState.Success(it)
            }
    }


}