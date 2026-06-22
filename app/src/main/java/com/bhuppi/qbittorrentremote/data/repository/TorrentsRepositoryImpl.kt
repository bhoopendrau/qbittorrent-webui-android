package com.bhuppi.qbittorrentremote.data.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.data.remote.api.TorrentsApi
import com.bhuppi.qbittorrentremote.data.remote.dto.toCategory
import com.bhuppi.qbittorrentremote.data.remote.dto.toTorrent
import com.bhuppi.qbittorrentremote.data.remote.dto.toTorrentFile
import com.bhuppi.qbittorrentremote.data.remote.dto.toTorrentProperties
import com.bhuppi.qbittorrentremote.data.remote.dto.toTracker
import com.bhuppi.qbittorrentremote.domain.model.Category
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.model.TorrentFile
import com.bhuppi.qbittorrentremote.domain.model.TorrentProperties
import com.bhuppi.qbittorrentremote.domain.model.Tracker
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

class TorrentsRepositoryImpl @Inject constructor(
    private val torrentsApi: TorrentsApi
) : TorrentsRepository {

    private fun List<String>.toPipeString() = joinToString("|")

    private fun checkResponse(response: retrofit2.Response<Void>): ApiSuccess {
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun getTorrentList(
        filter: String?, category: String?, sort: String?, reverse: Boolean?
    ): List<Torrent> {
        return torrentsApi.getTorrentList(filter = filter, category = category, sort = sort, reverse = reverse)
            .map { it.toTorrent() }
    }

    override suspend fun pauseTorrents(hashes: List<String>) = checkResponse(torrentsApi.pauseTorrents(hashes.toPipeString()))
    override suspend fun resumeTorrents(hashes: List<String>) = checkResponse(torrentsApi.resumeTorrents(hashes.toPipeString()))
    override suspend fun deleteTorrents(hashes: List<String>, deleteFiles: Boolean) = checkResponse(torrentsApi.deleteTorrents(hashes.toPipeString(), deleteFiles))

    override suspend fun addTorrents(urls: String?, savePath: String?, category: String?, tags: String?, paused: Boolean?): ApiSuccess {
        val textType = "text/plain".toMediaTypeOrNull()
        return checkResponse(torrentsApi.addTorrents(
            urls = urls?.toRequestBody(textType),
            savePath = savePath?.toRequestBody(textType),
            category = category?.toRequestBody(textType),
            tags = tags?.toRequestBody(textType),
            paused = paused?.toString()?.toRequestBody(textType)
        ))
    }

    override suspend fun recheckTorrents(hashes: List<String>) = checkResponse(torrentsApi.recheckTorrents(hashes.toPipeString()))
    override suspend fun reannounceTorrents(hashes: List<String>) = checkResponse(torrentsApi.reannounceTorrents(hashes.toPipeString()))
    override suspend fun setForceStart(hashes: List<String>, value: Boolean) = checkResponse(torrentsApi.setForceStart(hashes.toPipeString(), value))
    override suspend fun setTorrentCategory(hashes: List<String>, category: String) = checkResponse(torrentsApi.setTorrentCategory(hashes.toPipeString(), category))
    override suspend fun getCategories(): Map<String, Category> = torrentsApi.getCategories().mapValues { it.value.toCategory() }

    // Detail
    override suspend fun getTorrentProperties(hash: String): TorrentProperties = torrentsApi.getTorrentProperties(hash).toTorrentProperties()
    override suspend fun getTorrentTrackers(hash: String): List<Tracker> = torrentsApi.getTorrentTrackers(hash).map { it.toTracker() }
    override suspend fun getTorrentFiles(hash: String): List<TorrentFile> = torrentsApi.getTorrentFiles(hash).map { it.toTorrentFile() }

    // Priority
    override suspend fun increasePriority(hashes: List<String>) = checkResponse(torrentsApi.increasePriority(hashes.toPipeString()))
    override suspend fun decreasePriority(hashes: List<String>) = checkResponse(torrentsApi.decreasePriority(hashes.toPipeString()))
    override suspend fun topPriority(hashes: List<String>) = checkResponse(torrentsApi.topPriority(hashes.toPipeString()))
    override suspend fun bottomPriority(hashes: List<String>) = checkResponse(torrentsApi.bottomPriority(hashes.toPipeString()))

    // Per-torrent speed limits
    override suspend fun setTorrentDownloadLimit(hashes: List<String>, limit: Long) = checkResponse(torrentsApi.setTorrentDownloadLimit(hashes.toPipeString(), limit))
    override suspend fun setTorrentUploadLimit(hashes: List<String>, limit: Long) = checkResponse(torrentsApi.setTorrentUploadLimit(hashes.toPipeString(), limit))

    // File priority
    override suspend fun setFilePriority(hash: String, fileIds: List<Int>, priority: Int) =
        checkResponse(torrentsApi.setFilePriority(hash, fileIds.joinToString("|"), priority))

    // Tracker management
    override suspend fun addTrackers(hash: String, urls: List<String>) = checkResponse(torrentsApi.addTrackers(hash, urls.joinToString("\n")))
    override suspend fun removeTrackers(hash: String, urls: List<String>) = checkResponse(torrentsApi.removeTrackers(hash, urls.toPipeString()))

    // Torrent management
    override suspend fun renameTorrent(hash: String, name: String) = checkResponse(torrentsApi.renameTorrent(hash, name))
    override suspend fun setLocation(hashes: List<String>, location: String) = checkResponse(torrentsApi.setLocation(hashes.toPipeString(), location))
    override suspend fun toggleSequentialDownload(hashes: List<String>) = checkResponse(torrentsApi.toggleSequentialDownload(hashes.toPipeString()))
    override suspend fun toggleFirstLastPiecePrio(hashes: List<String>) = checkResponse(torrentsApi.toggleFirstLastPiecePrio(hashes.toPipeString()))
    override suspend fun setSuperSeeding(hashes: List<String>, value: Boolean) = checkResponse(torrentsApi.setSuperSeeding(hashes.toPipeString(), value))

    // Tags
    override suspend fun getTags(): List<String> = torrentsApi.getTags()
    override suspend fun addTorrentTags(hashes: List<String>, tags: String) = checkResponse(torrentsApi.addTorrentTags(hashes.toPipeString(), tags))
    override suspend fun removeTorrentTags(hashes: List<String>, tags: String) = checkResponse(torrentsApi.removeTorrentTags(hashes.toPipeString(), tags))
}
