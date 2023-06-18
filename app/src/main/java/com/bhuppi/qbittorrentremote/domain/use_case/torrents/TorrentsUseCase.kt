package com.bhuppi.qbittorrentremote.domain.use_case.torrents

import android.util.Log
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.repository.TorrentRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TorrentsUseCase @Inject constructor(
    private val repository: TorrentRepository
){
        fun getTorrents() = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getTorrentList()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            Log.e("Some", "Error httpException")
        } catch (e: IOException) {
            Log.e("Some", "Error httpException")
        }
    }
}