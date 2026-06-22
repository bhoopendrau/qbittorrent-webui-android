package com.bhuppi.qbittorrentremote.data.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.data.remote.api.AuthApi
import com.bhuppi.qbittorrentremote.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(username: String, password: String): ApiSuccess {
        val response = authApi.login(username, password)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return ApiSuccess(true)
    }

    override suspend fun logout(): ApiSuccess {
        val response = authApi.logout()
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return ApiSuccess(true)
    }
}
