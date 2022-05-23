package com.moabo.moviedemo.view.genreMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GenreViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel(){

private val _genreStateFlow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    var genreStateFlow : StateFlow<ApiState> = _genreStateFlow

    fun getGenres()=viewModelScope.launch {

        _genreStateFlow.value= ApiState.Loading
        mainRepository.getGenres()
            .catch {
            _genreStateFlow.value= ApiState.Failure(it)
        }
            .collect {
            _genreStateFlow.value= ApiState.Success(it.genres)
        }
    }
}