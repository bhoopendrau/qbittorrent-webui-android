package com.bhuppi.qbittorrentremote.data.repository

import android.util.Log
import com.bhuppi.qbittorrentremote.data.remote.api.TorrentsApi
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import javax.inject.Inject

class TorrentsRepositoryImpl @Inject constructor (
        private val torrentsApi: TorrentsApi
    ): TorrentsRepository {
    override suspend fun getTorrentList(): List<Torrent> {
        Log.i("Some", "Calling third party")
        var result =  torrentsApi.getTorrentList()
        Log.i("Some", "Called third party")
        return result
    }
}