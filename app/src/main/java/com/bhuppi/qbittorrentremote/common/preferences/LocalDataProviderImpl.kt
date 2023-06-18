package com.bhuppi.qbittorrentremote.common.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.bhuppi.qbittorrentremote.common.models.ServerDetails

class LocalDataProviderImpl (private val context: Context): LocalDataProvider {
    override fun getStoredCookie(): HashSet<String> {
        return PreferenceManager
            .getDefaultSharedPreferences(context)
            .getStringSet("Some", HashSet<String>()) as HashSet<String>
    }

    override fun setStoredCookie(cookie: HashSet<String>) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putStringSet("Some", cookie).apply()
        editor.apply()
    }

    override fun storeServerDetails(serverDetails: ServerDetails) {
        TODO("Not yet implemented")
    }

    override fun getServerDetails(): ServerDetails {
        TODO("Not yet implemented")
    }
}