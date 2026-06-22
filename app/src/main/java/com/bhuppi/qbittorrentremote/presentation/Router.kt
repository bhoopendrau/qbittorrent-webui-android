package com.bhuppi.qbittorrentremote.presentation

sealed class Router(val route: String) {
    object LoginScreen : Router("login")
    object TorrentList : Router("torrent_list")
    object AddTorrent : Router("add_torrent")
}
