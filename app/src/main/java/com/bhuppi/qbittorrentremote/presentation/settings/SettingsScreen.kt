package com.bhuppi.qbittorrentremote.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhuppi.qbittorrentremote.common.utils.formatFileSize
import com.bhuppi.qbittorrentremote.presentation.Router

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var showShutdownDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionHeader("Server Information")
            InfoRow("Server URL", state.serverUrl)
            InfoRow("qBittorrent Version", state.appVersion.ifBlank { "Loading..." })
            InfoRow("Web API Version", state.apiVersion.ifBlank { "Loading..." })
            InfoRow("Default Save Path", state.defaultSavePath.ifBlank { "Loading..." })

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            SectionHeader("Transfer")
            state.transferInfo?.let { info ->
                InfoRow("Connection Status", info.connectionStatus)
                InfoRow("DHT Nodes", info.dhtNodes.toString())
                InfoRow("Session Download", formatFileSize(info.dlInfoData))
                InfoRow("Session Upload", formatFileSize(info.upInfoData))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            SectionHeader("Speed Limits")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Alternative Speed Limits")
                Switch(
                    checked = state.isAltSpeedEnabled,
                    onCheckedChange = { viewModel.toggleAltSpeed() }
                )
            }
            InfoRow("Global DL Limit", if (state.globalDlLimit <= 0) "Unlimited" else "${formatFileSize(state.globalDlLimit)}/s")
            InfoRow("Global UL Limit", if (state.globalUlLimit <= 0) "Unlimited" else "${formatFileSize(state.globalUlLimit)}/s")

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            SectionHeader("Actions")
            OutlinedButton(
                onClick = { showShutdownDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Shutdown Server")
            }
            Button(
                onClick = {
                    viewModel.logout {
                        navController.navigate(Router.LoginScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }

    if (showShutdownDialog) {
        AlertDialog(
            onDismissRequest = { showShutdownDialog = false },
            title = { Text("Shutdown Server?") },
            text = { Text("This will shut down the qBittorrent application on the server. Are you sure?") },
            confirmButton = {
                TextButton(onClick = {
                    showShutdownDialog = false
                    viewModel.shutdown {
                        navController.navigate(Router.LoginScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }) { Text("Shutdown", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showShutdownDialog = false }) { Text("Cancel") }
            }
        )
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
