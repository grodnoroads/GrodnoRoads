package com.egoriku.grodnoroads.settings.changelog.data.dto

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ChangelogDTO(
    @SerialName("name")
    val name: String,

    @SerialName("code")
    val code: Int,

    @SerialName("notes")
    val notes: String,

    @SerialName("date")
    val releaseDate: Timestamp
)