package com.bhuppi.qbittorrentremote.presentation.torrent_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.TorrentsUseCase
import com.bhuppi.qbittorrentremote.presentation.auth.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TorrentListViewModel @Inject constructor(
    private val useCase: TorrentsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(TorrentListState())
    val state: State<TorrentListState> = _state

    init {
        useCase.getTorrents().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    Log.i("Some", "Success called ${result.data?.size}")
                    _state.value = TorrentListState(
                        data = result.data ?: ArrayList()
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