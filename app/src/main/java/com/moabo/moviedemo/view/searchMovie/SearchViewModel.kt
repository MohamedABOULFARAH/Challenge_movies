package com.moabo.moviedemo.view.searchMovie

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
class SearchViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _searchResultStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    var searchResultStateFlow: StateFlow<ApiState> = _searchResultStateFlow

    fun getSearchResults(query :String) = viewModelScope.launch {

        _searchResultStateFlow.value = ApiState.Loading
        mainRepository.getSearch(query)
            .catch {
                _searchResultStateFlow.value = ApiState.Failure(it)
            }
            .collect {
                _searchResultStateFlow.value = ApiState.Success(it.results)
            }
    }
}