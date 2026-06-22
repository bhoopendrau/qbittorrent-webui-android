package com.bhuppi.qbittorrentremote.presentation.torrent_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bhuppi.qbittorrentremote.common.utils.formatFileSize
import com.bhuppi.qbittorrentremote.common.utils.formatProgress
import com.bhuppi.qbittorrentremote.domain.model.TorrentFile

@Composable
fun FilesTab(
    files: List<TorrentFile>,
    onSetPriority: (fileIndex: Int, priority: Int) -> Unit
) {
    if (files.isEmpty()) {
        Text(
            text = "No files",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(files) { file ->
            FileItem(file = file, onSetPriority = onSetPriority)
            HorizontalDivider()
        }
    }
}

@Composable
private fun FileItem(
    file: TorrentFile,
    onSetPriority: (fileIndex: Int, priority: Int) -> Unit
) {
    var showPriorityMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = file.name.substringAfterLast("/"),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        LinearProgressIndicator(
            progress = { file.progress.toFloat() },
            modifier = Modifier.fillMaxWidth(),
            color = if (file.priority == 0) Color.Gray else MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${formatFileSize(file.size)} - ${formatProgress(file.progress)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            TextButton(onClick = { showPriorityMenu = true }) {
                Text(
                    text = file.priorityText,
                    style = MaterialTheme.typography.bodySmall
                )
                DropdownMenu(
                    expanded = showPriorityMenu,
                    onDismissRequest = { showPriorityMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Skip") },
                        onClick = { showPriorityMenu = false; onSetPriority(file.index, 0) }
                    )
                    DropdownMenuItem(
                        text = { Text("Normal") },
                        onClick = { showPriorityMenu = false; onSetPriority(file.index, 1) }
                    )
                    DropdownMenuItem(
                        text = { Text("High") },
                        onClick = { showPriorityMenu = false; onSetPriority(file.index, 6) }
                    )
                    DropdownMenuItem(
                        text = { Text("Maximum") },
                        onClick = { showPriorityMenu = false; onSetPriority(file.index, 7) }
                    )
                }
            }
        }

        if (file.name.contains("/")) {
            Text(
                text = file.name.substringBeforeLast("/"),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
