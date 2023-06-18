package com.bhuppi.qbittorrentremote.data.remote.dto

import com.bhuppi.qbittorrentremote.domain.model.BuildInfo

data class BuildInfoDto(
    val bitness: Int,
    val boost: String,
    val libtorrent: String,
    val openssl: String,
    val qt: String,
    val zlib: String
)

fun BuildInfoDto.toBuildInfo(): BuildInfo {
    return BuildInfo(
        bitness = bitness,
        boost = boost,
        libtorrent = libtorrent,
        openssl = openssl,
        qt = qt,
        zlib = zlib
    )
}