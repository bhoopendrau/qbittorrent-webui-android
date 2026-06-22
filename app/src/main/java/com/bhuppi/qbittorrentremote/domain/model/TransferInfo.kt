package com.bhuppi.qbittorrentremote.domain.model

data class TransferInfo(
    val dlInfoSpeed: Long = 0,
    val dlInfoData: Long = 0,
    val upInfoSpeed: Long = 0,
    val upInfoData: Long = 0,
    val dlRateLimit: Long = 0,
    val upRateLimit: Long = 0,
    val dhtNodes: Int = 0,
    val connectionStatus: String = ""
)
