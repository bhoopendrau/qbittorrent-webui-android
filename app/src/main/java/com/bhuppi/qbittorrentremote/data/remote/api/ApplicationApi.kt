package com.bhuppi.qbittorrentremote.data.remote.api

import com.bhuppi.qbittorrentremote.data.remote.dto.BuildInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApplicationApi {
    @GET("/api/v2/app/version")
    suspend fun getVersion(): String

    @GET("/api/v2/app/webapiVersion")
    suspend fun getWebApiVersion(): String

    @GET("/api/v2/app/buildInfo")
    suspend fun getBuildInfo(): BuildInfoDto

    @POST("/api/v2/app/shutdown")
    suspend fun shutdown(): Response<Void>

    @GET("/api/v2/app/defaultSavePath")
    suspend fun getDefaultSavePath(): String
}
