package com.bhuppi.qbittorrentremote.data.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.data.remote.api.TorrentsApi
import com.bhuppi.qbittorrentremote.data.remote.dto.toCategory
import com.bhuppi.qbittorrentremote.data.remote.dto.toTorrent
import com.bhuppi.qbittorrentremote.domain.model.Category
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

class TorrentsRepositoryImpl @Inject constructor(
    private val torrentsApi: TorrentsApi
) : TorrentsRepository {

    override suspend fun getTorrentList(
        filter: String?,
        category: String?,
        sort: String?,
        reverse: Boolean?
    ): List<Torrent> {
        return torrentsApi.getTorrentList(
            filter = filter,
            category = category,
            sort = sort,
            reverse = reverse
        ).map { it.toTorrent() }
    }

    override suspend fun pauseTorrents(hashes: List<String>): ApiSuccess {
        val response = torrentsApi.pauseTorrents(hashes.joinToString("|"))
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun resumeTorrents(hashes: List<String>): ApiSuccess {
        val response = torrentsApi.resumeTorrents(hashes.joinToString("|"))
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun deleteTorrents(hashes: List<String>, deleteFiles: Boolean): ApiSuccess {
        val response = torrentsApi.deleteTorrents(hashes.joinToString("|"), deleteFiles)
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun addTorrents(
        urls: String?,
        savePath: String?,
        category: String?,
        tags: String?,
        paused: Boolean?
    ): ApiSuccess {
        val textType = "text/plain".toMediaTypeOrNull()
        val response = torrentsApi.addTorrents(
            urls = urls?.toRequestBody(textType),
            savePath = savePath?.toRequestBody(textType),
            category = category?.toRequestBody(textType),
            tags = tags?.toRequestBody(textType),
            paused = paused?.toString()?.toRequestBody(textType)
        )
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun recheckTorrents(hashes: List<String>): ApiSuccess {
        val response = torrentsApi.recheckTorrents(hashes.joinToString("|"))
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun reannounceTorrents(hashes: List<String>): ApiSuccess {
        val response = torrentsApi.reannounceTorrents(hashes.joinToString("|"))
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun setForceStart(hashes: List<String>, value: Boolean): ApiSuccess {
        val response = torrentsApi.setForceStart(hashes.joinToString("|"), value)
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun setTorrentCategory(hashes: List<String>, category: String): ApiSuccess {
        val response = torrentsApi.setTorrentCategory(hashes.joinToString("|"), category)
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun getCategories(): Map<String, Category> {
        return torrentsApi.getCategories().mapValues { it.value.toCategory() }
    }
}
