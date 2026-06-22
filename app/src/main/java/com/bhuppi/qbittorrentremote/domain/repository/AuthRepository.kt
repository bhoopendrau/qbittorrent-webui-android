package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess

interface AuthRepository {
    suspend fun login(username: String, password: String): ApiSuccess
    suspend fun logout(): ApiSuccess
}
