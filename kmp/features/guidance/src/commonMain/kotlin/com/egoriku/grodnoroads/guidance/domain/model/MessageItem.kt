package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable

@Stable
data class MessageItem(
    val message: String,
    val source: Source
)