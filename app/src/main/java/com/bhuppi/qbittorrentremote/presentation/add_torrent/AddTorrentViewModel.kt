package com.bhuppi.qbittorrentremote.presentation.add_torrent

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.use_case.torrents.AddTorrentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddTorrentViewModel @Inject constructor(
    private val addTorrentUseCase: AddTorrentUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AddTorrentState())
    val state: State<AddTorrentState> = _state

    fun updateUrls(urls: String) {
        _state.value = _state.value.copy(urls = urls)
    }

    fun updateSavePath(path: String) {
        _state.value = _state.value.copy(savePath = path)
    }

    fun updateCategory(category: String) {
        _state.value = _state.value.copy(category = category)
    }

    fun updateTags(tags: String) {
        _state.value = _state.value.copy(tags = tags)
    }

    fun toggleStartPaused() {
        _state.value = _state.value.copy(startPaused = !_state.value.startPaused)
    }

    fun addTorrent() {
        val currentState = _state.value
        if (currentState.urls.isBlank()) {
            _state.value = currentState.copy(errorMessage = "Please enter at least one URL or magnet link")
            return
        }

        addTorrentUseCase(
            urls = currentState.urls,
            savePath = currentState.savePath.ifBlank { null },
            category = currentState.category.ifBlank { null },
            tags = currentState.tags.ifBlank { null },
            paused = if (currentState.startPaused) true else null
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, success = true, errorMessage = "")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "Failed to add torrent"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, errorMessage = "")
                }
            }
        }.launchIn(viewModelScope)
    }
}
