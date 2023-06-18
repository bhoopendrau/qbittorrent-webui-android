package com.bhuppi.qbittorrentremote.data.remote.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("/api/v2/auth/login")
    suspend fun login(@Field("username") userName:String, @Field("password") password:String)

}