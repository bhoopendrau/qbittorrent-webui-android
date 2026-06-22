package com.bhuppi.qbittorrentremote.presentation.torrent_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bhuppi.qbittorrentremote.common.utils.formatFileSize
import com.bhuppi.qbittorrentremote.common.utils.formatProgress
import com.bhuppi.qbittorrentremote.common.utils.formatSpeed
import com.bhuppi.qbittorrentremote.common.utils.mapTorrentState
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.domain.model.TorrentProperties
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GeneralTab(
    torrent: Torrent?,
    properties: TorrentProperties?
) {
    if (torrent == null && properties == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (torrent != null) {
            SectionHeader("Transfer")
            InfoRow("Status", mapTorrentState(torrent.state).displayName)
            InfoRow("Progress", formatProgress(torrent.progress))
            InfoRow("Download Speed", formatSpeed(torrent.dlspeed).ifEmpty { "0 B/s" })
            InfoRow("Upload Speed", formatSpeed(torrent.upspeed).ifEmpty { "0 B/s" })
            InfoRow("Seeds", "${torrent.numSeeds} (${torrent.numComplete} total)")
            InfoRow("Peers", "${torrent.numLeechs} (${torrent.numIncomplete} total)")
            InfoRow("Ratio", String.format(Locale.US, "%.2f", torrent.ratio))
        }

        if (properties != null) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            SectionHeader("Information")
            InfoRow("Total Size", formatFileSize(properties.totalSize))
            InfoRow("Total Downloaded", formatFileSize(properties.totalDownloaded))
            InfoRow("Total Uploaded", formatFileSize(properties.totalUploaded))
            InfoRow("Total Wasted", formatFileSize(properties.totalWasted))
            InfoRow("Save Path", properties.savePath)
            InfoRow("Connections", "${properties.nbConnections} / ${properties.nbConnectionsLimit}")
            InfoRow("Pieces", "${properties.piecesHave} / ${properties.piecesNum} (${formatFileSize(properties.pieceSize)} each)")

            if (properties.additionDate > 0) {
                InfoRow("Added On", formatTimestamp(properties.additionDate))
            }
            if (properties.completionDate > 0) {
                InfoRow("Completed On", formatTimestamp(properties.completionDate))
            }
            if (properties.createdBy.isNotBlank()) {
                InfoRow("Created By", properties.createdBy)
            }
            if (properties.comment.isNotBlank()) {
                InfoRow("Comment", properties.comment)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            SectionHeader("Speed Limits")
            InfoRow("DL Limit", if (properties.dlLimit <= 0) "Unlimited" else "${formatFileSize(properties.dlLimit)}/s")
            InfoRow("UL Limit", if (properties.upLimit <= 0) "Unlimited" else "${formatFileSize(properties.upLimit)}/s")
            InfoRow("Share Ratio", String.format(Locale.US, "%.3f", properties.shareRatio))
        }

        if (torrent != null) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            SectionHeader("Options")
            InfoRow("Sequential Download", if (torrent.seqDl) "Yes" else "No")
            InfoRow("First/Last Piece Priority", if (torrent.flPiecePrio) "Yes" else "No")
            InfoRow("Super Seeding", if (torrent.superSeeding) "Yes" else "No")
            InfoRow("Force Start", if (torrent.forceStart) "Yes" else "No")
            InfoRow("Auto TMM", if (torrent.autoTmm) "Yes" else "No")
            if (torrent.category.isNotBlank()) InfoRow("Category", torrent.category)
            if (torrent.tags.isNotBlank()) InfoRow("Tags", torrent.tags)
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.6f)
        )
    }
}

private fun formatTimestamp(epochSeconds: Long): String {
    if (epochSeconds <= 0) return "N/A"
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date(epochSeconds * 1000))
}
