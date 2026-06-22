package com.bhuppi.qbittorrentremote.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProvider
import com.bhuppi.qbittorrentremote.data.remote.api.ApplicationApi
import com.bhuppi.qbittorrentremote.domain.repository.TransferRepository
import com.bhuppi.qbittorrentremote.domain.use_case.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val transferRepository: TransferRepository,
    private val applicationApi: ApplicationApi,
    private val authUseCase: AuthUseCase,
    private val localDataProvider: LocalDataProvider
) : ViewModel() {
    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    init {
        loadAll()
    }

    private fun loadAll() {
        _state.value = _state.value.copy(
            serverUrl = localDataProvider.getServerDetails()?.baseUrl ?: ""
        )
        loadAppInfo()
        loadTransferInfo()
    }

    private fun loadAppInfo() {
        viewModelScope.launch {
            try {
                val version = applicationApi.getVersion()
                val apiVersion = applicationApi.getWebApiVersion()
                val defaultPath = applicationApi.getDefaultSavePath()
                _state.value = _state.value.copy(
                    appVersion = version,
                    apiVersion = apiVersion,
                    defaultSavePath = defaultPath
                )
            } catch (_: Exception) {}
        }
    }

    private fun loadTransferInfo() {
        viewModelScope.launch {
            try {
                val info = transferRepository.getTransferInfo()
                val altSpeed = transferRepository.getSpeedLimitsMode()
                val dlLimit = transferRepository.getDownloadLimit()
                val ulLimit = transferRepository.getUploadLimit()
                _state.value = _state.value.copy(
                    transferInfo = info,
                    isAltSpeedEnabled = altSpeed,
                    globalDlLimit = dlLimit,
                    globalUlLimit = ulLimit
                )
            } catch (_: Exception) {}
        }
    }

    fun toggleAltSpeed() {
        viewModelScope.launch {
            try {
                transferRepository.toggleSpeedLimitsMode()
                _state.value = _state.value.copy(isAltSpeedEnabled = !_state.value.isAltSpeedEnabled)
            } catch (_: Exception) {}
        }
    }

    fun setDownloadLimit(limit: Long) {
        viewModelScope.launch {
            try {
                transferRepository.setDownloadLimit(limit)
                _state.value = _state.value.copy(globalDlLimit = limit)
            } catch (_: Exception) {}
        }
    }

    fun setUploadLimit(limit: Long) {
        viewModelScope.launch {
            try {
                transferRepository.setUploadLimit(limit)
                _state.value = _state.value.copy(globalUlLimit = limit)
            } catch (_: Exception) {}
        }
    }

    fun shutdown(onDone: () -> Unit) {
        viewModelScope.launch {
            try {
                applicationApi.shutdown()
                onDone()
            } catch (_: Exception) {}
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        authUseCase.logout().onEach { result ->
            if (result is Resource.Success) onLoggedOut()
        }.launchIn(viewModelScope)
    }
}
