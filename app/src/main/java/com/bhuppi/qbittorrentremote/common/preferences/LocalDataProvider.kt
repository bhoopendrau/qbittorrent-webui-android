package com.bhuppi.qbittorrentremote.common.preferences

import com.bhuppi.qbittorrentremote.common.models.ServerDetails

interface LocalDataProvider {
    fun getStoredCookie(): HashSet<String>
    fun setStoredCookie(cookie: HashSet<String>)
    fun storeServerDetails(serverDetails: ServerDetails)
    fun getServerDetails(): ServerDetails?
    fun hasServerDetails(): Boolean
    fun clearServerDetails()
    fun clearCookies()
}
