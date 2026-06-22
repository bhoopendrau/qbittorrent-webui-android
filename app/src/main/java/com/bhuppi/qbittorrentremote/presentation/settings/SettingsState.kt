package com.bhuppi.qbittorrentremote.presentation.settings

import com.bhuppi.qbittorrentremote.domain.model.TransferInfo

data class SettingsState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val serverUrl: String = "",
    val appVersion: String = "",
    val apiVersion: String = "",
    val defaultSavePath: String = "",
    val transferInfo: TransferInfo? = null,
    val isAltSpeedEnabled: Boolean = false,
    val globalDlLimit: Long = 0,
    val globalUlLimit: Long = 0
)
