package com.bhuppi.qbittorrentremote.presentation.torrent_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.DeleteTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.PauseTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.ResumeTorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.TorrentsUseCase
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TorrentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val torrentsUseCase: TorrentsUseCase,
    private val pauseTorrentsUseCase: PauseTorrentsUseCase,
    private val resumeTorrentsUseCase: ResumeTorrentsUseCase,
    private val deleteTorrentsUseCase: DeleteTorrentsUseCase,
    private val repository: TorrentsRepository
) : ViewModel() {
    private val _state = mutableStateOf(TorrentDetailState())
    val state: State<TorrentDetailState> = _state

    val hash: String = savedStateHandle["hash"] ?: ""
    private var refreshJob: Job? = null

    init {
        if (hash.isNotBlank()) {
            loadAll()
            startAutoRefresh()
        }
    }

    private fun startAutoRefresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (true) {
                delay(5000)
                loadAll()
            }
        }
    }

    private fun loadAll() {
        loadTorrentInfo()
        loadProperties()
        loadTrackers()
        loadFiles()
    }

    private fun loadTorrentInfo() {
        torrentsUseCase.getTorrents(filter = null).onEach { result ->
            if (result is Resource.Success) {
                val torrent = result.data?.find { it.hash == hash }
                if (torrent != null) {
                    _state.value = _state.value.copy(torrent = torrent)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadProperties() {
        viewModelScope.launch {
            try {
                val props = repository.getTorrentProperties(hash)
                _state.value = _state.value.copy(properties = props, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(errorMessage = e.message ?: "Failed to load properties", isLoading = false)
            }
        }
    }

    private fun loadTrackers() {
        viewModelScope.launch {
            try {
                val trackers = repository.getTorrentTrackers(hash)
                _state.value = _state.value.copy(trackers = trackers)
            } catch (_: Exception) {}
        }
    }

    private fun loadFiles() {
        viewModelScope.launch {
            try {
                val files = repository.getTorrentFiles(hash)
                _state.value = _state.value.copy(files = files)
            } catch (_: Exception) {}
        }
    }

    fun selectTab(index: Int) {
        _state.value = _state.value.copy(selectedTab = index)
    }

    fun pauseTorrent() {
        pauseTorrentsUseCase(listOf(hash)).onEach { result ->
            if (result is Resource.Success) loadAll()
        }.launchIn(viewModelScope)
    }

    fun resumeTorrent() {
        resumeTorrentsUseCase(listOf(hash)).onEach { result ->
            if (result is Resource.Success) loadAll()
        }.launchIn(viewModelScope)
    }

    fun deleteTorrent(deleteFiles: Boolean, onDeleted: () -> Unit) {
        deleteTorrentsUseCase(listOf(hash), deleteFiles).onEach { result ->
            if (result is Resource.Success) {
                refreshJob?.cancel()
                onDeleted()
            }
        }.launchIn(viewModelScope)
    }

    fun recheckTorrent() {
        viewModelScope.launch {
            try { repository.recheckTorrents(listOf(hash)); loadAll() } catch (_: Exception) {}
        }
    }

    fun reannounceTorrent() {
        viewModelScope.launch {
            try { repository.reannounceTorrents(listOf(hash)); loadAll() } catch (_: Exception) {}
        }
    }

    fun setFilePriority(fileIndex: Int, priority: Int) {
        viewModelScope.launch {
            try { repository.setFilePriority(hash, listOf(fileIndex), priority); loadFiles() } catch (_: Exception) {}
        }
    }

    fun toggleSequentialDownload() {
        viewModelScope.launch {
            try { repository.toggleSequentialDownload(listOf(hash)); loadAll() } catch (_: Exception) {}
        }
    }

    fun toggleFirstLastPiecePrio() {
        viewModelScope.launch {
            try { repository.toggleFirstLastPiecePrio(listOf(hash)); loadAll() } catch (_: Exception) {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
    }
}
