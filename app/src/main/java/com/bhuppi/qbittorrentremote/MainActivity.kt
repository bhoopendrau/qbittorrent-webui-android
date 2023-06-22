package com.bhuppi.qbittorrentremote

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhuppi.qbittorrentremote.presentation.Router
import com.bhuppi.qbittorrentremote.presentation.torrent_list.TorrentListScreen
import com.bhuppi.qbittorrentremote.ui.theme.QbittorrentRemoteClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QbittorrentRemoteClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController();
                    NavHost(
                        navController = navController,
                        startDestination = Router.LoginScreen.route
                    ) {
                        composable(
                            route = Router.LoginScreen.route
                        ) {
                            LoginScreen(navController)
                        }
                        composable(
                            route = Router.TorrentList.route
                        ) {
                            TorrentListScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QbittorrentRemoteClientTheme {
        Greeting("Android")
    }
}