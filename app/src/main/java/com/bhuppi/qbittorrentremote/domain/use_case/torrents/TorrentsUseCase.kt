package com.bhuppi.qbittorrentremote.domain.use_case.torrents

import android.util.Log
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TorrentsUseCase @Inject constructor(
    private val repository: TorrentsRepository
) {
    fun getTorrents(
        filter: String? = null,
        category: String? = null,
        sort: String? = null,
        reverse: Boolean? = null
    ): Flow<Resource<List<Torrent>>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getTorrentList(filter, category, sort, reverse)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            Log.e("TorrentsUseCase", "HTTP error", e)
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            Log.e("TorrentsUseCase", "IO error", e)
            emit(Resource.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            Log.e("TorrentsUseCase", "Unknown error", e)
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
