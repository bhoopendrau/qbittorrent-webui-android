package com.bhuppi.qbittorrentremote.domain.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.domain.model.TransferInfo

interface TransferRepository {
    suspend fun getTransferInfo(): TransferInfo
    suspend fun getSpeedLimitsMode(): Boolean
    suspend fun toggleSpeedLimitsMode(): ApiSuccess
    suspend fun getDownloadLimit(): Long
    suspend fun setDownloadLimit(limit: Long): ApiSuccess
    suspend fun getUploadLimit(): Long
    suspend fun setUploadLimit(limit: Long): ApiSuccess
}
