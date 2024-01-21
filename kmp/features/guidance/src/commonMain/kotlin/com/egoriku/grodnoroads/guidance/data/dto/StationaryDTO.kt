package com.egoriku.grodnoroads.guidance.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class StationaryDTO(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("angle")
    val angle: Float,

    @SerialName("bidirectional")
    val bidirectional: Boolean,

    @SerialName("update_time")
    val updateTime: Long,

    @SerialName("speed_car")
    val speedCar: Int,

    @SerialName("speed_truck")
    val speedTruck: Int,
)