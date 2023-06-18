package com.bhuppi.qbittorrentremote.common.models

data class ServerDetails(
    val baseUrl: String,
    val port: Int,
    val user: User
)
