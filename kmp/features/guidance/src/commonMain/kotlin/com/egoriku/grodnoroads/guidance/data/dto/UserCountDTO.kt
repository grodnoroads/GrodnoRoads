package com.egoriku.grodnoroads.guidance.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserCountDTO(
    @SerialName("count")
    val count: Int
)