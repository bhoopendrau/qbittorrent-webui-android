package com.bhuppi.qbittorrentremote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhuppi.qbittorrentremote.presentation.Router
import com.bhuppi.qbittorrentremote.presentation.add_torrent.AddTorrentScreen
import com.bhuppi.qbittorrentremote.presentation.auth.LoginScreen
import com.bhuppi.qbittorrentremote.presentation.torrent_list.TorrentListScreen
import com.bhuppi.qbittorrentremote.ui.theme.QbittorrentRemoteClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QbittorrentRemoteClientTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Router.LoginScreen.route
                    ) {
                        composable(route = Router.LoginScreen.route) {
                            LoginScreen(navController)
                        }
                        composable(route = Router.TorrentList.route) {
                            TorrentListScreen(navController)
                        }
                        composable(route = Router.AddTorrent.route) {
                            AddTorrentScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
