package com.bhuppi.qbittorrentremote.common.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.bhuppi.qbittorrentremote.common.models.ServerDetails

class LocalDataProviderImpl(private val context: Context) : LocalDataProvider {

    private val prefs get() = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getStoredCookie(): HashSet<String> {
        return prefs.getStringSet(KEY_COOKIES, HashSet()) as HashSet<String>
    }

    override fun setStoredCookie(cookie: HashSet<String>) {
        prefs.edit().putStringSet(KEY_COOKIES, cookie).apply()
    }

    override fun storeServerDetails(serverDetails: ServerDetails) {
        prefs.edit()
            .putString(KEY_BASE_URL, serverDetails.baseUrl)
            .putString(KEY_USERNAME, serverDetails.username)
            .putString(KEY_PASSWORD, serverDetails.password)
            .apply()
    }

    override fun getServerDetails(): ServerDetails? {
        val baseUrl = prefs.getString(KEY_BASE_URL, null) ?: return null
        val username = prefs.getString(KEY_USERNAME, "") ?: ""
        val password = prefs.getString(KEY_PASSWORD, "") ?: ""
        return ServerDetails(baseUrl, username, password)
    }

    override fun hasServerDetails(): Boolean {
        return prefs.getString(KEY_BASE_URL, null) != null
    }

    override fun clearServerDetails() {
        prefs.edit()
            .remove(KEY_BASE_URL)
            .remove(KEY_USERNAME)
            .remove(KEY_PASSWORD)
            .apply()
    }

    override fun clearCookies() {
        prefs.edit().remove(KEY_COOKIES).apply()
    }

    companion object {
        private const val KEY_COOKIES = "stored_cookies"
        private const val KEY_BASE_URL = "server_base_url"
        private const val KEY_USERNAME = "server_username"
        private const val KEY_PASSWORD = "server_password"
    }
}
