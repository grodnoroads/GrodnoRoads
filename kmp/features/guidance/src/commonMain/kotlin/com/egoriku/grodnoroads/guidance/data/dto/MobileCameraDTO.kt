package com.egoriku.grodnoroads.guidance.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class MobileCameraDTO(
    @SerialName("name")
    val name: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("speed")
    val speed: Int
)