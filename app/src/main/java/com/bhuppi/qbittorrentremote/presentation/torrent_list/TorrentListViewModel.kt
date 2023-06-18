package com.bhuppi.qbittorrentremote.presentation.torrent_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.TorrentsUseCase
import com.bhuppi.qbittorrentremote.presentation.auth.LoginState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TorrentListViewModel @Inject constructor(
    private val useCase: TorrentsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(TorrentListState())
    val state: State<TorrentListState> = _state

    fun login () {
        useCase.getTorrents().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = TorrentListState(
                        data = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = TorrentListState(
                        errorMessage = result.message ?: "Something went wrong "
                    )
                }
                is Resource.Loading -> {
                    _state.value = TorrentListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}