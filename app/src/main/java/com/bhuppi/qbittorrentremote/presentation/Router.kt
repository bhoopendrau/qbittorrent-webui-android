package com.bhuppi.qbittorrentremote.presentation

sealed class Router (val route: String) {
    object LoginScreen: Router("login")
}