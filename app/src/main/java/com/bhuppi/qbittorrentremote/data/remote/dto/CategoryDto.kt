package com.bhuppi.qbittorrentremote.data.remote.dto

import com.bhuppi.qbittorrentremote.domain.model.Category

data class CategoryDto(
    val name: String = "",
    val savePath: String = ""
)

fun CategoryDto.toCategory(): Category {
    return Category(name = name, savePath = savePath)
}
