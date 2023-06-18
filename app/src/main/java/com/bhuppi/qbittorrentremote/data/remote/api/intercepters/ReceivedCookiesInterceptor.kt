package com.bhuppi.qbittorrentremote.data.remote.api.intercepters

import android.util.Log
import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ReceivedCookiesInterceptor @Inject constructor(
    private var localStorage: LocalDataProvider
    ) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if(originalResponse.headers("set-cookie").isNotEmpty()) {
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("set-cookie")) {
                cookies.add(header)
            }
            Log.i("SOME","came here with ${originalResponse.headers("set-cookie")}")
            localStorage.setStoredCookie(cookies)
        }
        return originalResponse
    }
}