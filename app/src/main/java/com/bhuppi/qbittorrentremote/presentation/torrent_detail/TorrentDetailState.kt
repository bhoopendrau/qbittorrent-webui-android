package com.bhuppi.qbittorrentremote.presentation.torrent_detail

import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.model.TorrentFile
import com.bhuppi.qbittorrentremote.domain.model.TorrentProperties
import com.bhuppi.qbittorrentremote.domain.model.Tracker

data class TorrentDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val torrent: Torrent? = null,
    val properties: TorrentProperties? = null,
    val trackers: List<Tracker> = emptyList(),
    val files: List<TorrentFile> = emptyList(),
    val selectedTab: Int = 0
)
