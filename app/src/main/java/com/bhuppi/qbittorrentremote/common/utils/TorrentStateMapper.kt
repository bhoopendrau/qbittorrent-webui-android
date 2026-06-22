package com.bhuppi.qbittorrentremote.common.utils

import androidx.compose.ui.graphics.Color

data class TorrentStateInfo(
    val displayName: String,
    val color: Color,
    val canPause: Boolean,
    val canResume: Boolean
)

fun mapTorrentState(state: String): TorrentStateInfo {
    return when (state) {
        "error" -> TorrentStateInfo("Error", Color(0xFFE53935), canPause = false, canResume = true)
        "missingFiles" -> TorrentStateInfo("Missing Files", Color(0xFFE53935), canPause = false, canResume = true)
        "uploading" -> TorrentStateInfo("Seeding", Color(0xFF43A047), canPause = true, canResume = false)
        "pausedUP" -> TorrentStateInfo("Paused", Color(0xFF757575), canPause = false, canResume = true)
        "queuedUP" -> TorrentStateInfo("Queued Seed", Color(0xFF43A047), canPause = true, canResume = false)
        "stalledUP" -> TorrentStateInfo("Seeding", Color(0xFF66BB6A), canPause = true, canResume = false)
        "checkingUP" -> TorrentStateInfo("Checking", Color(0xFFFFA726), canPause = true, canResume = false)
        "forcedUP" -> TorrentStateInfo("Forced Seed", Color(0xFF43A047), canPause = true, canResume = false)
        "allocating" -> TorrentStateInfo("Allocating", Color(0xFF42A5F5), canPause = true, canResume = false)
        "downloading" -> TorrentStateInfo("Downloading", Color(0xFF42A5F5), canPause = true, canResume = false)
        "metaDL" -> TorrentStateInfo("Fetching Metadata", Color(0xFF42A5F5), canPause = true, canResume = false)
        "forcedMetaDL" -> TorrentStateInfo("Forced Metadata", Color(0xFF42A5F5), canPause = true, canResume = false)
        "pausedDL" -> TorrentStateInfo("Paused", Color(0xFF757575), canPause = false, canResume = true)
        "queuedDL" -> TorrentStateInfo("Queued", Color(0xFF42A5F5), canPause = true, canResume = false)
        "stalledDL" -> TorrentStateInfo("Stalled", Color(0xFFFFA726), canPause = true, canResume = false)
        "checkingDL" -> TorrentStateInfo("Checking", Color(0xFFFFA726), canPause = true, canResume = false)
        "forcedDL" -> TorrentStateInfo("Forced DL", Color(0xFF42A5F5), canPause = true, canResume = false)
        "checkingResumeData" -> TorrentStateInfo("Checking", Color(0xFFFFA726), canPause = false, canResume = false)
        "moving" -> TorrentStateInfo("Moving", Color(0xFFFFA726), canPause = false, canResume = false)
        else -> TorrentStateInfo("Unknown", Color(0xFF757575), canPause = false, canResume = false)
    }
}
