package com.bhuppi.qbittorrentremote.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
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

    fun login () {
        authUseCase.login().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = LoginState(
                        success = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = LoginState(
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}