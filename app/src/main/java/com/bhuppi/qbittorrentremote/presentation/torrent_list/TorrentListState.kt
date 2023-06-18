package com.bhuppi.qbittorrentremote.presentation.torrent_list

import com.bhuppi.qbittorrentremote.domain.model.Torrent

data class TorrentListState (
    val isLoading: Boolean = false,
    val data: List<Torrent>? = null,
    val errorMessage: String = ""
)