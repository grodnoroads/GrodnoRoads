package com.egoriku.grodnoroads.settings.faq.domain.model

import androidx.compose.runtime.Stable

@Stable
data class FAQ(
    val question: String,
    val answer: String
)