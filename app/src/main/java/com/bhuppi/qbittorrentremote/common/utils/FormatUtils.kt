package com.bhuppi.qbittorrentremote.common.utils

import java.util.Locale

fun formatFileSize(bytes: Long): String {
    if (bytes <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(bytes.toDouble()) / Math.log10(1024.0)).toInt()
    val index = digitGroups.coerceAtMost(units.size - 1)
    return String.format(Locale.US, "%.1f %s", bytes / Math.pow(1024.0, index.toDouble()), units[index])
}

fun formatSpeed(bytesPerSecond: Int): String {
    if (bytesPerSecond <= 0) return ""
    return "${formatFileSize(bytesPerSecond.toLong())}/s"
}

fun formatEta(seconds: Int): String {
    if (seconds < 0 || seconds == 8640000) return ""
    if (seconds == 0) return "0s"
    val days = seconds / 86400
    val hours = (seconds % 86400) / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return buildString {
        if (days > 0) append("${days}d ")
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m ")
        if (days == 0 && hours == 0 && minutes == 0) append("${secs}s")
    }.trim()
}

fun formatProgress(progress: Double): String {
    return String.format(Locale.US, "%.1f%%", progress * 100)
}
