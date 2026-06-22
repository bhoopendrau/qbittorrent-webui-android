package com.bhuppi.qbittorrentremote.presentation

sealed class Router(val route: String) {
    object LoginScreen : Router("login")
    object TorrentList : Router("torrent_list")
    object AddTorrent : Router("add_torrent")
    object TorrentDetail : Router("torrent_detail/{hash}") {
        fun createRoute(hash: String) = "torrent_detail/$hash"
    }
    object Settings : Router("settings")
}
