package com.moabo.moviedemo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.Util.ApiState
import com.moabo.moviedemo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _topRatedResultStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var topRatedResultStateFlow: StateFlow<ApiState> = _topRatedResultStateFlow

    fun getTopRatedResults(page :Int) = viewModelScope.launch {

        _topRatedResultStateFlow.value = ApiState.Loading
        mainRepository.getTopRatedMovies(page)
            .catch {
                _topRatedResultStateFlow.value = ApiState.Failure(it)
            }
            .collect {
                _topRatedResultStateFlow.value = ApiState.Success(it.results)
            }
    }
}