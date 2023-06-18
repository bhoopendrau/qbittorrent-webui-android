package com.bhuppi.qbittorrentremote.presentation.auth

import com.bhuppi.qbittorrentremote.common.models.ApiSuccess

data class LoginState(
    val isLoading: Boolean = false,
    val success: ApiSuccess? = null,
    val errorMessage: String = ""
)