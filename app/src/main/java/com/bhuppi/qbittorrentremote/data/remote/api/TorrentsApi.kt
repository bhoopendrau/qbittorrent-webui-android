package com.bhuppi.qbittorrentremote.data.remote.api

import com.bhuppi.qbittorrentremote.data.remote.dto.CategoryDto
import com.bhuppi.qbittorrentremote.data.remote.dto.TorrentDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface TorrentsApi {

    @GET("/api/v2/torrents/info")
    suspend fun getTorrentList(
        @Query("filter") filter: String? = null,
        @Query("category") category: String? = null,
        @Query("tag") tag: String? = null,
        @Query("sort") sort: String? = null,
        @Query("reverse") reverse: Boolean? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("hashes") hashes: String? = null
    ): List<TorrentDto>

    @FormUrlEncoded
    @POST("/api/v2/torrents/pause")
    suspend fun pauseTorrents(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/resume")
    suspend fun resumeTorrents(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/delete")
    suspend fun deleteTorrents(
        @Field("hashes") hashes: String,
        @Field("deleteFiles") deleteFiles: Boolean
    ): Response<Void>

    @Multipart
    @POST("/api/v2/torrents/add")
    suspend fun addTorrents(
        @Part("urls") urls: RequestBody? = null,
        @Part torrents: List<MultipartBody.Part>? = null,
        @Part("savepath") savePath: RequestBody? = null,
        @Part("category") category: RequestBody? = null,
        @Part("tags") tags: RequestBody? = null,
        @Part("paused") paused: RequestBody? = null,
        @Part("autoTMM") autoTMM: RequestBody? = null
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/recheck")
    suspend fun recheckTorrents(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/reannounce")
    suspend fun reannounceTorrents(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setForceStart")
    suspend fun setForceStart(
        @Field("hashes") hashes: String,
        @Field("value") value: Boolean
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setCategory")
    suspend fun setTorrentCategory(
        @Field("hashes") hashes: String,
        @Field("category") category: String
    ): Response<Void>

    @GET("/api/v2/torrents/categories")
    suspend fun getCategories(): Map<String, CategoryDto>
}
