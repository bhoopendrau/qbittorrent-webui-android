package com.bhuppi.qbittorrentremote.domain.use_case.auth

import android.util.Log
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.common.models.User
import com.bhuppi.qbittorrentremote.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    fun login () = flow<Resource<ApiSuccess>> {
        try {
            emit(Resource.Loading())
            val result = repository.login(User("bhuppi", "18120120229"))
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            Log.e("Some", "Error httpException")
        } catch (e: IOException) {
            Log.e("Some", "Error httpException")
        }
    }
}