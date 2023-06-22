package com.bhuppi.qbittorrentremote.domain.use_case.torrents

import android.util.Log
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.repository.TorrentsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TorrentsUseCase @Inject constructor(
    private val repository: TorrentsRepository
){
        fun getTorrents() = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getTorrentList()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            Log.e("Some", "Some http Error")
            emit(Resource.Error<List<Torrent>>(e.message()))
        } catch (e: IOException) {
            Log.e("Some", "Some IOError")
            emit(Resource.Error<List<Torrent>>("Error HttpException"))
        } catch (e: Exception) {
            Log.e("Some", "Some unknown error ${e.message}")
            emit(Resource.Error<List<Torrent>>(e.message ?: "Unknown Error"))
        }
    }
}