package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.location.LatLng

@Stable
data class LastLocation(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {
    companion object {
        val UNKNOWN_LOCATION = LatLng(0.0, 0.0)

        val None = LastLocation(
            latLng = UNKNOWN_LOCATION,
            bearing = -1f,
            speed = 0
        )
    }
}