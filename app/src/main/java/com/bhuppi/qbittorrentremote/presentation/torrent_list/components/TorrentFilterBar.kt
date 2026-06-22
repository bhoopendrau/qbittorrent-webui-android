package com.bhuppi.qbittorrentremote.presentation.torrent_list.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class TorrentFilter(val label: String, val value: String?)

val TORRENT_FILTERS = listOf(
    TorrentFilter("All", null),
    TorrentFilter("Downloading", "downloading"),
    TorrentFilter("Seeding", "seeding"),
    TorrentFilter("Completed", "completed"),
    TorrentFilter("Paused", "paused"),
    TorrentFilter("Active", "active"),
    TorrentFilter("Inactive", "inactive"),
    TorrentFilter("Stalled", "stalled"),
    TorrentFilter("Errored", "errored")
)

@Composable
fun TorrentFilterBar(
    selectedFilter: String?,
    onFilterSelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TORRENT_FILTERS.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter.value,
                onClick = { onFilterSelected(filter.value) },
                label = { Text(filter.label) }
            )
        }
    }
}
