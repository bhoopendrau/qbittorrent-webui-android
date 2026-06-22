package com.bhuppi.qbittorrentremote.presentation.torrent_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhuppi.qbittorrentremote.common.utils.mapTorrentState
import com.bhuppi.qbittorrentremote.presentation.Router
import com.bhuppi.qbittorrentremote.presentation.torrent_list.components.TorrentFilterBar
import com.bhuppi.qbittorrentremote.presentation.torrent_list.components.TorrentListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorrentListScreen(
    navController: NavController,
    viewModel: TorrentListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (state.isMultiSelectMode) {
                        Text("${state.selectedHashes.size} selected")
                    } else {
                        Text("qBittorrent Remote")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    if (state.isMultiSelectMode) {
                        IconButton(onClick = { viewModel.clearSelection() }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear selection")
                        }
                    }
                },
                actions = {
                    if (state.isMultiSelectMode) {
                        IconButton(onClick = { viewModel.pauseSelected() }) {
                            Icon(Icons.Default.Pause, contentDescription = "Pause selected")
                        }
                        IconButton(onClick = { viewModel.resumeSelected() }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Resume selected")
                        }
                        IconButton(onClick = { viewModel.showDeleteDialog() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete selected")
                        }
                    } else {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Logout") },
                                onClick = {
                                    showMenu = false
                                    viewModel.logout {
                                        navController.navigate(Router.LoginScreen.route) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (!state.isMultiSelectMode) {
                FloatingActionButton(
                    onClick = { navController.navigate(Router.AddTorrent.route) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add torrent")
                }
            }
        },
        bottomBar = {
            if (state.isMultiSelectMode) {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { viewModel.pauseSelected() },
                            modifier = Modifier.weight(1f)
                        ) { Text("Pause") }
                        TextButton(
                            onClick = { viewModel.resumeSelected() },
                            modifier = Modifier.weight(1f)
                        ) { Text("Resume") }
                        TextButton(
                            onClick = { viewModel.showDeleteDialog() },
                            modifier = Modifier.weight(1f)
                        ) { Text("Delete") }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TorrentFilterBar(
                selectedFilter = state.filter,
                onFilterSelected = { viewModel.setFilter(it) }
            )

            Box(modifier = Modifier.weight(1f)) {
                if (state.data.isEmpty() && !state.isLoading && state.errorMessage.isBlank()) {
                    Text(
                        text = "No torrents found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.data, key = { it.hash }) { torrent ->
                        val stateInfo = mapTorrentState(torrent.state)
                        TorrentListItem(
                            torrent = torrent,
                            isSelected = state.selectedHashes.contains(torrent.hash),
                            onClick = {
                                if (state.isMultiSelectMode) {
                                    viewModel.toggleSelection(torrent.hash)
                                } else {
                                    if (stateInfo.canPause) {
                                        viewModel.pauseSingle(torrent.hash)
                                    } else if (stateInfo.canResume) {
                                        viewModel.resumeSingle(torrent.hash)
                                    }
                                }
                            },
                            onLongClick = {
                                viewModel.toggleSelection(torrent.hash)
                            }
                        )
                        HorizontalDivider()
                    }
                }

                if (state.isLoading && state.data.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                if (state.errorMessage.isNotBlank()) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    if (state.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDeleteDialog() },
            title = { Text("Delete ${state.selectedHashes.size} torrent(s)?") },
            text = { Text("Choose whether to also delete the downloaded files.") },
            confirmButton = {
                TextButton(onClick = { viewModel.deleteSelected(deleteFiles = true) }) {
                    Text("Delete with files")
                }
            },
            dismissButton = {
                Row {
                    TextButton(onClick = { viewModel.dismissDeleteDialog() }) {
                        Text("Cancel")
                    }
                    TextButton(onClick = { viewModel.deleteSelected(deleteFiles = false) }) {
                        Text("Remove only")
                    }
                }
            }
        )
    }
}
