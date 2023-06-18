package com.bhuppi.qbittorrentremote.data.remote.api.intercepters

import com.bhuppi.qbittorrentremote.common.preferences.LocalDataProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AddCookiesInterceptor @Inject constructor(
    private val localStorage: LocalDataProvider
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences:HashSet<String> =  localStorage.getStoredCookie()
        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie)
            print("here $cookie.toString()")
        }
        print("nothing happened")
        return chain.proceed(builder.build())
    }
}