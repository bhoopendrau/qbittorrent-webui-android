package com.bhuppi.qbittorrentremote.presentation.add_torrent

data class AddTorrentState(
    val urls: String = "",
    val savePath: String = "",
    val category: String = "",
    val tags: String = "",
    val startPaused: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val success: Boolean = false
)
