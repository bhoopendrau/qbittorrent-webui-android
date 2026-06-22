package com.bhuppi.qbittorrentremote.data.remote.api

import com.bhuppi.qbittorrentremote.data.remote.dto.CategoryDto
import com.bhuppi.qbittorrentremote.data.remote.dto.TorrentDto
import com.bhuppi.qbittorrentremote.data.remote.dto.TorrentFileDto
import com.bhuppi.qbittorrentremote.data.remote.dto.TorrentPropertiesDto
import com.bhuppi.qbittorrentremote.data.remote.dto.TrackerDto
import com.bhuppi.qbittorrentremote.data.remote.dto.WebSeedDto
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

    // Detail endpoints
    @GET("/api/v2/torrents/properties")
    suspend fun getTorrentProperties(@Query("hash") hash: String): TorrentPropertiesDto

    @GET("/api/v2/torrents/trackers")
    suspend fun getTorrentTrackers(@Query("hash") hash: String): List<TrackerDto>

    @GET("/api/v2/torrents/files")
    suspend fun getTorrentFiles(@Query("hash") hash: String): List<TorrentFileDto>

    @GET("/api/v2/torrents/webseeds")
    suspend fun getTorrentWebSeeds(@Query("hash") hash: String): List<WebSeedDto>

    @GET("/api/v2/torrents/pieceStates")
    suspend fun getTorrentPieceStates(@Query("hash") hash: String): List<Int>

    // Priority
    @FormUrlEncoded
    @POST("/api/v2/torrents/increasePrio")
    suspend fun increasePriority(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/decreasePrio")
    suspend fun decreasePriority(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/topPrio")
    suspend fun topPriority(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/bottomPrio")
    suspend fun bottomPriority(@Field("hashes") hashes: String): Response<Void>

    // Per-torrent speed limits
    @FormUrlEncoded
    @POST("/api/v2/torrents/setDownloadLimit")
    suspend fun setTorrentDownloadLimit(
        @Field("hashes") hashes: String,
        @Field("limit") limit: Long
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setUploadLimit")
    suspend fun setTorrentUploadLimit(
        @Field("hashes") hashes: String,
        @Field("limit") limit: Long
    ): Response<Void>

    // File priority
    @FormUrlEncoded
    @POST("/api/v2/torrents/filePrio")
    suspend fun setFilePriority(
        @Field("hash") hash: String,
        @Field("id") fileIds: String,
        @Field("priority") priority: Int
    ): Response<Void>

    // Tracker management
    @FormUrlEncoded
    @POST("/api/v2/torrents/addTrackers")
    suspend fun addTrackers(
        @Field("hash") hash: String,
        @Field("urls") urls: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/editTracker")
    suspend fun editTracker(
        @Field("hash") hash: String,
        @Field("origUrl") origUrl: String,
        @Field("newUrl") newUrl: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeTrackers")
    suspend fun removeTrackers(
        @Field("hash") hash: String,
        @Field("urls") urls: String
    ): Response<Void>

    // Torrent management
    @FormUrlEncoded
    @POST("/api/v2/torrents/rename")
    suspend fun renameTorrent(
        @Field("hash") hash: String,
        @Field("name") name: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setLocation")
    suspend fun setLocation(
        @Field("hashes") hashes: String,
        @Field("location") location: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setAutoManagement")
    suspend fun setAutoManagement(
        @Field("hashes") hashes: String,
        @Field("enable") enable: Boolean
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/toggleSequentialDownload")
    suspend fun toggleSequentialDownload(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/toggleFirstLastPiecePrio")
    suspend fun toggleFirstLastPiecePrio(@Field("hashes") hashes: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setSuperSeeding")
    suspend fun setSuperSeeding(
        @Field("hashes") hashes: String,
        @Field("value") value: Boolean
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/setShareLimits")
    suspend fun setShareLimits(
        @Field("hashes") hashes: String,
        @Field("ratioLimit") ratioLimit: Double,
        @Field("seedingTimeLimit") seedingTimeLimit: Long,
        @Field("inactiveSeedingTimeLimit") inactiveSeedingTimeLimit: Long
    ): Response<Void>

    // Tags
    @GET("/api/v2/torrents/tags")
    suspend fun getTags(): List<String>

    @FormUrlEncoded
    @POST("/api/v2/torrents/createTags")
    suspend fun createTags(@Field("tags") tags: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/deleteTags")
    suspend fun deleteTags(@Field("tags") tags: String): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/addTags")
    suspend fun addTorrentTags(
        @Field("hashes") hashes: String,
        @Field("tags") tags: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeTags")
    suspend fun removeTorrentTags(
        @Field("hashes") hashes: String,
        @Field("tags") tags: String
    ): Response<Void>

    // Category management
    @FormUrlEncoded
    @POST("/api/v2/torrents/createCategory")
    suspend fun createCategory(
        @Field("category") name: String,
        @Field("savePath") savePath: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/editCategory")
    suspend fun editCategory(
        @Field("category") name: String,
        @Field("savePath") savePath: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/removeCategories")
    suspend fun removeCategories(@Field("categories") categories: String): Response<Void>

    // File/folder rename
    @FormUrlEncoded
    @POST("/api/v2/torrents/renameFile")
    suspend fun renameFile(
        @Field("hash") hash: String,
        @Field("oldPath") oldPath: String,
        @Field("newPath") newPath: String
    ): Response<Void>

    @FormUrlEncoded
    @POST("/api/v2/torrents/renameFolder")
    suspend fun renameFolder(
        @Field("hash") hash: String,
        @Field("oldPath") oldPath: String,
        @Field("newPath") newPath: String
    ): Response<Void>
}
