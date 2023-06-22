package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.domain.model.Torrent

interface TorrentsRepository {
    suspend fun getTorrentList(): List<Torrent>
}