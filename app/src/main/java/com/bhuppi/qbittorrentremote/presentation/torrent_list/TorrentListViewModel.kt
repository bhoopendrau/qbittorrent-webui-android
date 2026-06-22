package com.bhuppi.qbittorrentremote.presentation.torrent_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.use_case.auth.AuthUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.DeleteTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.PauseTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.ResumeTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.TorrentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TorrentListViewModel @Inject constructor(
    private val torrentsUseCase: TorrentsUseCase,
    private val pauseTorrentsUseCase: PauseTorrentsUseCase,
    private val resumeTorrentsUseCase: ResumeTorrentsUseCase,
    private val deleteTorrentsUseCase: DeleteTorrentsUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _state = mutableStateOf(TorrentListState())
    val state: State<TorrentListState> = _state

    private var refreshJob: Job? = null

    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (true) {
                loadTorrents()
                delay(3000)
            }
        }
    }

    private fun loadTorrents() {
        torrentsUseCase.getTorrents(filter = _state.value.filter).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        data = result.data ?: emptyList(),
                        isLoading = false,
                        errorMessage = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }
                is Resource.Loading -> {
                    if (_state.value.data.isEmpty()) {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setFilter(filter: String?) {
        _state.value = _state.value.copy(filter = filter)
        loadTorrents()
    }

    fun toggleSelection(hash: String) {
        val current = _state.value.selectedHashes.toMutableSet()
        if (current.contains(hash)) {
            current.remove(hash)
        } else {
            current.add(hash)
        }
        _state.value = _state.value.copy(
            selectedHashes = current,
            isMultiSelectMode = current.isNotEmpty()
        )
    }

    fun clearSelection() {
        _state.value = _state.value.copy(
            selectedHashes = emptySet(),
            isMultiSelectMode = false
        )
    }

    fun pauseSelected() {
        val hashes = _state.value.selectedHashes.toList()
        if (hashes.isEmpty()) return
        pauseTorrentsUseCase(hashes).onEach { result ->
            if (result is Resource.Success) {
                clearSelection()
                loadTorrents()
            }
        }.launchIn(viewModelScope)
    }

    fun resumeSelected() {
        val hashes = _state.value.selectedHashes.toList()
        if (hashes.isEmpty()) return
        resumeTorrentsUseCase(hashes).onEach { result ->
            if (result is Resource.Success) {
                clearSelection()
                loadTorrents()
            }
        }.launchIn(viewModelScope)
    }

    fun showDeleteDialog() {
        _state.value = _state.value.copy(showDeleteDialog = true)
    }

    fun dismissDeleteDialog() {
        _state.value = _state.value.copy(showDeleteDialog = false)
    }

    fun deleteSelected(deleteFiles: Boolean) {
        val hashes = _state.value.selectedHashes.toList()
        if (hashes.isEmpty()) return
        deleteTorrentsUseCase(hashes, deleteFiles).onEach { result ->
            if (result is Resource.Success) {
                _state.value = _state.value.copy(showDeleteDialog = false)
                clearSelection()
                loadTorrents()
            }
        }.launchIn(viewModelScope)
    }

    fun pauseSingle(hash: String) {
        pauseTorrentsUseCase(listOf(hash)).onEach { result ->
            if (result is Resource.Success) loadTorrents()
        }.launchIn(viewModelScope)
    }

    fun resumeSingle(hash: String) {
        resumeTorrentsUseCase(listOf(hash)).onEach { result ->
            if (result is Resource.Success) loadTorrents()
        }.launchIn(viewModelScope)
    }

    fun logout(onLoggedOut: () -> Unit) {
        authUseCase.logout().onEach { result ->
            if (result is Resource.Success) {
                refreshJob?.cancel()
                onLoggedOut()
            }
        }.launchIn(viewModelScope)
    }
}
