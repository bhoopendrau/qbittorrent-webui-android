package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.domain.model.Torrent

interface TorrentRepository {
    suspend fun getTorrentList(): List<Torrent>
}