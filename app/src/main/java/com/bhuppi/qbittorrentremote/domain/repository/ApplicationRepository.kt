package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.domain.model.BuildInfo

interface ApplicationRepository {
    suspend fun getBuildInfo(): BuildInfo
}