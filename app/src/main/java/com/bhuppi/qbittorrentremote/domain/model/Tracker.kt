package com.bhuppi.qbittorrentremote.domain.model

data class Tracker(
    val url: String = "",
    val status: Int = 0,
    val tier: Int = 0,
    val numPeers: Int = 0,
    val numSeeds: Int = 0,
    val numLeeches: Int = 0,
    val numDownloaded: Int = 0,
    val msg: String = ""
) {
    val statusText: String
        get() = when (status) {
            0 -> "Disabled"
            1 -> "Not contacted"
            2 -> "Working"
            3 -> "Updating"
            4 -> "Not working"
            else -> "Unknown"
        }
}
