package com.moabo.moviedemo.view.favoriteMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.model.MovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val movieDao: MovieDao,
    ) : ViewModel() {

    private val _favoritesResultStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var favoritesResultStateFlow: StateFlow<ApiState> = _favoritesResultStateFlow

    fun getFavoritesResults(page :Int) = viewModelScope.launch {

        _favoritesResultStateFlow.value = ApiState.Loading
        if (movieDao.getFavoriteMovies().isNotEmpty()){
            getFavoriteMovies()
        }else {
            _favoritesResultStateFlow.value = ApiState.Empty
        }

        }



    private fun getFavoriteMovies() = viewModelScope.launch {
        _favoritesResultStateFlow.value = ApiState.Success(movieDao.getFavoriteMovies())
    }
}