package com.bhuppi.qbittorrentremote.presentation.torrent_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bhuppi.qbittorrentremote.domain.model.Tracker

@Composable
fun TrackersTab(trackers: List<Tracker>) {
    if (trackers.isEmpty()) {
        Text(
            text = "No trackers",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(trackers) { tracker ->
            TrackerItem(tracker)
            HorizontalDivider()
        }
    }
}

@Composable
private fun TrackerItem(tracker: Tracker) {
    val statusColor = when (tracker.status) {
        2 -> Color(0xFF43A047)
        3 -> Color(0xFFFFA726)
        4 -> Color(0xFFE53935)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = tracker.url,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = tracker.statusText,
                style = MaterialTheme.typography.bodySmall,
                color = statusColor
            )
            Text(
                text = "Seeds: ${tracker.numSeeds} | Peers: ${tracker.numPeers}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (tracker.msg.isNotBlank()) {
            Text(
                text = tracker.msg,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
