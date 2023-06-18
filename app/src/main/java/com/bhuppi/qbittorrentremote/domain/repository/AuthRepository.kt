package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.common.models.User

interface AuthRepository {
    suspend fun login(user: User): ApiSuccess
    suspend fun logout(): ApiSuccess
}