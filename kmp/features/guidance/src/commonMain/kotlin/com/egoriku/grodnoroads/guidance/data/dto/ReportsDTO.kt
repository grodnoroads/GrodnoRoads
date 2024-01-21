package com.egoriku.grodnoroads.guidance.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ReportsDTO(
    @SerialName("type")
    val type: String,

    @SerialName("short_message")
    val shortMessage: String,

    @SerialName("message")
    val message: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("timestamp")
    val timestamp: Long,

    @SerialName("source")
    val source: String
)