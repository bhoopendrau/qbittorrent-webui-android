package com.bhuppi.qbittorrentremote.presentation.torrent_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhuppi.qbittorrentremote.domain.model.Torrent
import com.bhuppi.qbittorrentremote.presentation.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun TorrentListScreen (
    navController: NavController,
    viewModel: TorrentListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        Log.i("Some", "state data ${state.data.size}")
        LazyColumn() {
                items(state.data) { item: Torrent ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.name}",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
        if (state.errorMessage.isNotBlank()) {
            Text(
                text = state.errorMessage,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}