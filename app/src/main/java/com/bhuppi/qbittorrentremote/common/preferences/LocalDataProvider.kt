package com.bhuppi.qbittorrentremote.common.preferences

import com.bhuppi.qbittorrentremote.common.models.ServerDetails
import com.bhuppi.qbittorrentremote.common.models.User

interface LocalDataProvider  {
    fun getStoredCookie(): HashSet<String>
    fun setStoredCookie(cookie: HashSet<String>)
    fun storeServerDetails(serverDetails: ServerDetails)
    fun getServerDetails(): ServerDetails
}