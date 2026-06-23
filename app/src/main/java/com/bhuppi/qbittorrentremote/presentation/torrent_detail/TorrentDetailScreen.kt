package com.bhuppi.qbittorrentremote.presentation.torrent_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhuppi.qbittorrentremote.common.utils.mapTorrentState
import com.bhuppi.qbittorrentremote.presentation.torrent_detail.components.FilesTab
import com.bhuppi.qbittorrentremote.presentation.torrent_detail.components.GeneralTab
import com.bhuppi.qbittorrentremote.presentation.torrent_detail.components.TrackersTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorrentDetailScreen(
    navController: NavController,
    viewModel: TorrentDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    val tabs = listOf("General", "Trackers", "Files")
    val stateInfo = state.torrent?.let { mapTorrentState(it.state) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.torrent?.name ?: "Torrent Details",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (stateInfo != null) {
                        if (stateInfo.canPause) {
                            IconButton(onClick = { viewModel.pauseTorrent() }) {
                                Icon(Icons.Default.Pause, contentDescription = "Pause")
                            }
                        }
                        if (stateInfo.canResume) {
                            IconButton(onClick = { viewModel.resumeTorrent() }) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Resume")
                            }
                        }
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("Force Recheck") },
                            onClick = { showMenu = false; viewModel.recheckTorrent() }
                        )
                        DropdownMenuItem(
                            text = { Text("Reannounce") },
                            onClick = { showMenu = false; viewModel.reannounceTorrent() }
                        )
                        DropdownMenuItem(
                            text = { Text("Toggle Sequential Download") },
                            onClick = { showMenu = false; viewModel.toggleSequentialDownload() }
                        )
                        DropdownMenuItem(
                            text = { Text("Toggle First/Last Piece Priority") },
                            onClick = { showMenu = false; viewModel.toggleFirstLastPiecePrio() }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PrimaryTabRow(selectedTabIndex = state.selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = state.selectedTab == index,
                        onClick = { viewModel.selectTab(index) },
                        text = { Text(title) }
                    )
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                when (state.selectedTab) {
                    0 -> GeneralTab(torrent = state.torrent, properties = state.properties)
                    1 -> TrackersTab(trackers = state.trackers)
                    2 -> FilesTab(files = state.files, onSetPriority = { idx, prio -> viewModel.setFilePriority(idx, prio) })
                }

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete torrent?") },
            text = { Text("Choose whether to also delete the downloaded files.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteTorrent(deleteFiles = true) { navController.popBackStack() }
                }) { Text("Delete with files") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteTorrent(deleteFiles = false) { navController.popBackStack() }
                }) { Text("Remove only") }
            }
        )
    }
}
