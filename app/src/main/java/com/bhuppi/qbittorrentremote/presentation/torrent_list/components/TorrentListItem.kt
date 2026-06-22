package com.bhuppi.qbittorrentremote.presentation.torrent_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bhuppi.qbittorrentremote.common.utils.formatEta
import com.bhuppi.qbittorrentremote.common.utils.formatFileSize
import com.bhuppi.qbittorrentremote.common.utils.formatSpeed
import com.bhuppi.qbittorrentremote.common.utils.mapTorrentState
import com.bhuppi.qbittorrentremote.domain.model.Torrent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TorrentListItem(
    torrent: Torrent,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val stateInfo = mapTorrentState(torrent.state)
    val selectedColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    } else {
        Color.Transparent
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(selectedColor)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(stateInfo.color)
            )
            Text(
                text = torrent.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        LinearProgressIndicator(
            progress = { torrent.progress.toFloat() },
            modifier = Modifier.fillMaxWidth(),
            color = stateInfo.color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sizeText = "${formatFileSize(torrent.completed)} / ${formatFileSize(torrent.size)}"
            Text(
                text = sizeText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stateInfo.displayName,
                style = MaterialTheme.typography.bodySmall,
                color = stateInfo.color
            )
        }

        val dlSpeed = formatSpeed(torrent.dlspeed)
        val ulSpeed = formatSpeed(torrent.upspeed)
        val eta = formatEta(torrent.eta)
        if (dlSpeed.isNotEmpty() || ulSpeed.isNotEmpty() || eta.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val speedParts = mutableListOf<String>()
                if (dlSpeed.isNotEmpty()) speedParts.add("↓ $dlSpeed")
                if (ulSpeed.isNotEmpty()) speedParts.add("↑ $ulSpeed")
                Text(
                    text = speedParts.joinToString("  "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (eta.isNotEmpty()) {
                    Text(
                        text = "ETA: $eta",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        if (torrent.category.isNotEmpty() || torrent.tags.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (torrent.category.isNotEmpty()) {
                    Text(
                        text = torrent.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.shapes.extraSmall
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                if (torrent.tags.isNotEmpty()) {
                    torrent.tags.split(",").take(3).forEach { tag ->
                        val trimmed = tag.trim()
                        if (trimmed.isNotEmpty()) {
                            Text(
                                text = trimmed,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer,
                                        MaterialTheme.shapes.extraSmall
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
