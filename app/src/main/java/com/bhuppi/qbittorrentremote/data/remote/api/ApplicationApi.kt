package com.bhuppi.qbittorrentremote.data.remote.api

import com.bhuppi.qbittorrentremote.data.remote.dto.BuildInfoDto
import retrofit2.http.GET

interface ApplicationApi {
    @GET("/api/v2/auth/buildInfo")
    suspend fun getBuildInfo() : BuildInfoDto
}