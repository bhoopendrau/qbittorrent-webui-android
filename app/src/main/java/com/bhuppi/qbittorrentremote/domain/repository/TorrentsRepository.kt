package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.domain.model.Category
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.model.TorrentFile
import com.bhuppi.qbittorrentremote.domain.model.TorrentProperties
import com.bhuppi.qbittorrentremote.domain.model.Tracker

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

    // Detail
    suspend fun getTorrentProperties(hash: String): TorrentProperties
    suspend fun getTorrentTrackers(hash: String): List<Tracker>
    suspend fun getTorrentFiles(hash: String): List<TorrentFile>

    // Priority
    suspend fun increasePriority(hashes: List<String>): ApiSuccess
    suspend fun decreasePriority(hashes: List<String>): ApiSuccess
    suspend fun topPriority(hashes: List<String>): ApiSuccess
    suspend fun bottomPriority(hashes: List<String>): ApiSuccess

    // Per-torrent speed limits
    suspend fun setTorrentDownloadLimit(hashes: List<String>, limit: Long): ApiSuccess
    suspend fun setTorrentUploadLimit(hashes: List<String>, limit: Long): ApiSuccess

    // File priority
    suspend fun setFilePriority(hash: String, fileIds: List<Int>, priority: Int): ApiSuccess

    // Tracker management
    suspend fun addTrackers(hash: String, urls: List<String>): ApiSuccess
    suspend fun removeTrackers(hash: String, urls: List<String>): ApiSuccess

    // Torrent management
    suspend fun renameTorrent(hash: String, name: String): ApiSuccess
    suspend fun setLocation(hashes: List<String>, location: String): ApiSuccess
    suspend fun toggleSequentialDownload(hashes: List<String>): ApiSuccess
    suspend fun toggleFirstLastPiecePrio(hashes: List<String>): ApiSuccess
    suspend fun setSuperSeeding(hashes: List<String>, value: Boolean): ApiSuccess

    // Tags
    suspend fun getTags(): List<String>
    suspend fun addTorrentTags(hashes: List<String>, tags: String): ApiSuccess
    suspend fun removeTorrentTags(hashes: List<String>, tags: String): ApiSuccess
}
