package com.bhuppi.qbittorrentremote.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.use_case.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun updateServerUrl(url: String) {
        _state.value = _state.value.copy(serverUrl = url)
    }

    fun updateUsername(username: String) {
        _state.value = _state.value.copy(username = username)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun login() {
        val currentState = _state.value
        if (currentState.serverUrl.isBlank() || currentState.username.isBlank() || currentState.password.isBlank()) {
            _state.value = currentState.copy(errorMessage = "All fields are required")
            return
        }
        authUseCase.login(currentState.serverUrl, currentState.username, currentState.password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, success = result.data, errorMessage = "")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, errorMessage = "")
                }
            }
        }.launchIn(viewModelScope)
    }
}
