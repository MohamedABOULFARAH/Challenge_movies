package com.moabo.moviedemo.view.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moabo.moviedemo.utils.ApiState
import com.moabo.moviedemo.model.session.SessionRS
import com.moabo.moviedemo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SessionViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel(){

private val _sessionStateFlow:MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    var sessionStateFlow : StateFlow<ApiState> = _sessionStateFlow

    fun getSession()=viewModelScope.launch {

        _sessionStateFlow.value= ApiState.Loading
        mainRepository.getSession()
            .catch {
            _sessionStateFlow.value= ApiState.Failure(it)
        }
            .collect {
            _sessionStateFlow.value= ApiState.Success(it)
                SessionRS.UserSession=it
        }
    }
}