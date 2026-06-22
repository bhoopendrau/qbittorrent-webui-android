package com.bhuppi.qbittorrentremote.presentation.torrent_list

import com.bhuppi.qbittorrentremote.domain.model.Torrent

data class TorrentListState(
    val isLoading: Boolean = false,
    val data: List<Torrent> = emptyList(),
    val errorMessage: String = "",
    val selectedHashes: Set<String> = emptySet(),
    val filter: String? = null,
    val isMultiSelectMode: Boolean = false,
    val showDeleteDialog: Boolean = false
)
