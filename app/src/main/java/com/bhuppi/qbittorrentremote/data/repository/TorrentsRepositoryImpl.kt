package com.bhuppi.qbittorrentremote.data.repository

import com.bhuppi.qbittorrentremote.data.remote.api.TorrentsApi
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.repository.TorrentRepository
import javax.inject.Inject

class TorrentsRepositoryImpl @Inject constructor (
        private val torrentsApi: TorrentsApi
    ): TorrentRepository {
    override suspend fun getTorrentList(): List<Torrent> {
        return torrentsApi.getTorrentList()
    }
}