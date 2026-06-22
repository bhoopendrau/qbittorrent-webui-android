package com.bhuppi.qbittorrentremote.data.remote.dto

import com.bhuppi.qbittorrentremote.domain.model.WebSeed

data class WebSeedDto(
    val url: String = ""
)

fun WebSeedDto.toWebSeed(): WebSeed {
    return WebSeed(url = url)
}
