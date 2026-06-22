package com.bhuppi.qbittorrentremote.domain.model

data class TorrentFile(
    val index: Int = 0,
    val name: String = "",
    val size: Long = 0,
    val progress: Double = 0.0,
    val priority: Int = 0,
    val isSeed: Boolean = false,
    val availability: Double = 0.0
) {
    val priorityText: String
        get() = when (priority) {
            0 -> "Skip"
            1 -> "Normal"
            6 -> "High"
            7 -> "Maximum"
            else -> "Normal"
        }
}
