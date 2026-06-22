package com.bhuppi.qbittorrentremote.domain.use_case.torrents

import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddTorrentUseCase @Inject constructor(
    private val repository: TorrentsRepository
) {
    operator fun invoke(
        urls: String?,
        savePath: String? = null,
        category: String? = null,
        tags: String? = null,
        paused: Boolean? = null
    ): Flow<Resource<ApiSuccess>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.addTorrents(urls, savePath, category, tags, paused)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error("Failed to add torrent: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
