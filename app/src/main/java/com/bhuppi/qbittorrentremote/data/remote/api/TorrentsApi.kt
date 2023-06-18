package com.bhuppi.qbittorrentremote.data.remote.api

import com.bhuppi.qbittorrentremote.domain.model.Torrent
import retrofit2.http.GET

interface TorrentsApi {
    @GET("/api/v2/torrents/info")
    suspend fun getTorrentList(): List<Torrent>
}