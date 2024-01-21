package com.egoriku.grodnoroads.settings.changelog.domain.model

import androidx.compose.runtime.Stable

@Stable
data class ReleaseNotes(
    val versionCode: Int,
    val versionName: String,
    val notes: String,
    val releaseDate: String
)