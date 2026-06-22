package com.bhuppi.qbittorrentremote.domain.use_case.auth

import android.util.Log
import com.bhuppi.qbittorrentremote.common.Resource
import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.common.models.ServerDetails
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProvider
import com.bhuppi.qbittorrentremote.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val localDataProvider: LocalDataProvider
) {
    fun login(serverUrl: String, username: String, password: String): Flow<Resource<ApiSuccess>> = flow {
        try {
            emit(Resource.Loading())
            localDataProvider.storeServerDetails(ServerDetails(serverUrl, username, password))
            val result = repository.login(username, password)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            Log.e("AuthUseCase", "Login failed", e)
            localDataProvider.clearServerDetails()
            localDataProvider.clearCookies()
            emit(Resource.Error("Login failed: ${e.message()}"))
        } catch (e: IOException) {
            Log.e("AuthUseCase", "Network error", e)
            localDataProvider.clearServerDetails()
            localDataProvider.clearCookies()
            emit(Resource.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            Log.e("AuthUseCase", "Unexpected error", e)
            localDataProvider.clearServerDetails()
            localDataProvider.clearCookies()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }

    fun logout(): Flow<Resource<ApiSuccess>> = flow {
        try {
            emit(Resource.Loading())
            repository.logout()
            localDataProvider.clearCookies()
            localDataProvider.clearServerDetails()
            emit(Resource.Success(ApiSuccess(true)))
        } catch (e: Exception) {
            localDataProvider.clearCookies()
            localDataProvider.clearServerDetails()
            emit(Resource.Success(ApiSuccess(true)))
        }
    }
}
