package com.bhuppi.qbittorrentremote.domain.use_case.torrents

import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteTorrentsUseCase @Inject constructor(
    private val repository: TorrentsRepository
) {
    operator fun invoke(hashes: List<String>, deleteFiles: Boolean): Flow<Resource<ApiSuccess>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.deleteTorrents(hashes, deleteFiles)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error("Failed to delete: ${e.message()}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
