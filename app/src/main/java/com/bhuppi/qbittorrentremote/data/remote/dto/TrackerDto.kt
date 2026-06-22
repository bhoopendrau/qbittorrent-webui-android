package com.bhuppi.qbittorrentremote.data.remote.dto

import com.bhuppi.qbittorrentremote.domain.model.Tracker
import com.google.gson.annotations.SerializedName

data class TrackerDto(
    val url: String = "",
    val status: Int = 0,
    val tier: Int = 0,
    @SerializedName("num_peers") val numPeers: Int = 0,
    @SerializedName("num_seeds") val numSeeds: Int = 0,
    @SerializedName("num_leeches") val numLeeches: Int = 0,
    @SerializedName("num_downloaded") val numDownloaded: Int = 0,
    val msg: String = ""
)

fun TrackerDto.toTracker(): Tracker {
    return Tracker(
        url = url,
        status = status,
        tier = tier,
        numPeers = numPeers,
        numSeeds = numSeeds,
        numLeeches = numLeeches,
        numDownloaded = numDownloaded,
        msg = msg
    )
}
