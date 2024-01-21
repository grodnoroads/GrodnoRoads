package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface Alert {
    val id: String

    data class IncidentAlert(
        val mapEventType: MapEventType,
        val distance: Int,
        val messages: ImmutableList<MessageItem>,
        override val id: String
    ) : Alert

    data class CameraAlert(
        val cameraType: CameraType,
        val distance: Int,
        val speedLimit: Int,
        override val id: String
    ) : Alert
}