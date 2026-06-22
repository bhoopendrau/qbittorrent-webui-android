package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.domain.model.Category
import com.bhuppi.qbittorrentremote.domain.model.Torrent

interface TorrentsRepository {
    suspend fun getTorrentList(
        filter: String? = null,
        category: String? = null,
        sort: String? = null,
        reverse: Boolean? = null
    ): List<Torrent>

    suspend fun pauseTorrents(hashes: List<String>): ApiSuccess
    suspend fun resumeTorrents(hashes: List<String>): ApiSuccess
    suspend fun deleteTorrents(hashes: List<String>, deleteFiles: Boolean): ApiSuccess
    suspend fun addTorrents(urls: String?, savePath: String?, category: String?, tags: String?, paused: Boolean?): ApiSuccess
    suspend fun recheckTorrents(hashes: List<String>): ApiSuccess
    suspend fun reannounceTorrents(hashes: List<String>): ApiSuccess
    suspend fun setForceStart(hashes: List<String>, value: Boolean): ApiSuccess
    suspend fun setTorrentCategory(hashes: List<String>, category: String): ApiSuccess
    suspend fun getCategories(): Map<String, Category>
}
