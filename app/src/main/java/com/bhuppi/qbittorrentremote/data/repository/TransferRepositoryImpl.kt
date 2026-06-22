package com.bhuppi.qbittorrentremote.data.repository

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess
import com.bhuppi.qbittorrentremote.data.remote.api.TransferApi
import com.bhuppi.qbittorrentremote.data.remote.dto.toTransferInfo
import com.bhuppi.qbittorrentremote.domain.model.TransferInfo
import com.bhuppi.qbittorrentremote.domain.repository.TransferRepository
import retrofit2.HttpException
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val transferApi: TransferApi
) : TransferRepository {

    override suspend fun getTransferInfo(): TransferInfo = transferApi.getTransferInfo().toTransferInfo()

    override suspend fun getSpeedLimitsMode(): Boolean = transferApi.getSpeedLimitsMode() == 1

    override suspend fun toggleSpeedLimitsMode(): ApiSuccess {
        val response = transferApi.toggleSpeedLimitsMode()
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun getDownloadLimit(): Long = transferApi.getDownloadLimit()

    override suspend fun setDownloadLimit(limit: Long): ApiSuccess {
        val response = transferApi.setDownloadLimit(limit)
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }

    override suspend fun getUploadLimit(): Long = transferApi.getUploadLimit()

    override suspend fun setUploadLimit(limit: Long): ApiSuccess {
        val response = transferApi.setUploadLimit(limit)
        if (!response.isSuccessful) throw HttpException(response)
        return ApiSuccess(true)
    }
}
