package com.bhuppi.qbittorrentremote.data.remote.dto

import com.bhuppi.qbittorrentremote.domain.model.TorrentFile
import com.google.gson.annotations.SerializedName

data class TorrentFileDto(
    val index: Int = 0,
    val name: String = "",
    val size: Long = 0,
    val progress: Double = 0.0,
    val priority: Int = 0,
    @SerializedName("is_seed") val isSeed: Boolean = false,
    val availability: Double = 0.0
)

fun TorrentFileDto.toTorrentFile(): TorrentFile {
    return TorrentFile(
        index = index,
        name = name,
        size = size,
        progress = progress,
        priority = priority,
        isSeed = isSeed,
        availability = availability
    )
}
