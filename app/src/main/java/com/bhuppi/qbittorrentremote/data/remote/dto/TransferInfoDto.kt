package com.bhuppi.qbittorrentremote.data.remote.dto

import com.bhuppi.qbittorrentremote.domain.model.TransferInfo
import com.google.gson.annotations.SerializedName

data class TransferInfoDto(
    @SerializedName("dl_info_speed") val dlInfoSpeed: Long = 0,
    @SerializedName("dl_info_data") val dlInfoData: Long = 0,
    @SerializedName("up_info_speed") val upInfoSpeed: Long = 0,
    @SerializedName("up_info_data") val upInfoData: Long = 0,
    @SerializedName("dl_rate_limit") val dlRateLimit: Long = 0,
    @SerializedName("up_rate_limit") val upRateLimit: Long = 0,
    @SerializedName("dht_nodes") val dhtNodes: Int = 0,
    @SerializedName("connection_status") val connectionStatus: String = ""
)

fun TransferInfoDto.toTransferInfo(): TransferInfo {
    return TransferInfo(
        dlInfoSpeed = dlInfoSpeed,
        dlInfoData = dlInfoData,
        upInfoSpeed = upInfoSpeed,
        upInfoData = upInfoData,
        dlRateLimit = dlRateLimit,
        upRateLimit = upRateLimit,
        dhtNodes = dhtNodes,
        connectionStatus = connectionStatus
    )
}
