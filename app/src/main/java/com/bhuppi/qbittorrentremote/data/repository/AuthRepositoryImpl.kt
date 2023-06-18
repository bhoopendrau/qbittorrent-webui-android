package com.bhuppi.qbittorrentremote.data.repository

import android.content.Context
import android.util.Log
import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.data.remote.api.AuthApi
import com.bhuppi.qbittorrentremote.data.remote.api.intercepters.ReceivedCookiesInterceptor
import com.bhuppi.qbittorrentremote.common.models.User
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProvider
import com.bhuppi.qbittorrentremote.domain.repository.AuthRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (var authApi: AuthApi): AuthRepository {
    override suspend fun login(user: User): ApiSuccess {
        Log.i("SOME","came here")
        authApi.login(user.username, user.password)
        Log.i("SOME","came here")
        return ApiSuccess(true);
    }

    override suspend fun logout(): ApiSuccess {
        return ApiSuccess(true)
    }
}