package com.egoriku.grodnoroads.settings.faq.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class FaqDTO(
    @SerialName("q")
    val question: String,

    @SerialName("a")
    val answer: String
)