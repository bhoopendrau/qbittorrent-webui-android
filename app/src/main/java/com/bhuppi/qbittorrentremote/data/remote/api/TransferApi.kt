package com.bhuppi.qbittorrentremote.data.remote.api

import com.bhuppi.qbittorrentremote.data.remote.dto.TransferInfoDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TransferApi {
    @GET("/api/v2/transfer/info")
    suspend fun getTransferInfo(): TransferInfoDto

    @GET("/api/v2/transfer/speedLimitsMode")
    suspend fun getSpeedLimitsMode(): Int

    @POST("/api/v2/transfer/toggleSpeedLimitsMode")
    suspend fun toggleSpeedLimitsMode(): Response<Void>

    @GET("/api/v2/transfer/downloadLimit")
    suspend fun getDownloadLimit(): Long

    @FormUrlEncoded
    @POST("/api/v2/transfer/setDownloadLimit")
    suspend fun setDownloadLimit(@Field("limit") limit: Long): Response<Void>

    @GET("/api/v2/transfer/uploadLimit")
    suspend fun getUploadLimit(): Long

    @FormUrlEncoded
    @POST("/api/v2/transfer/setUploadLimit")
    suspend fun setUploadLimit(@Field("limit") limit: Long): Response<Void>
}
