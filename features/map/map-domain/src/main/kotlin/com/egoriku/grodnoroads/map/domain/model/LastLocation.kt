package com.egoriku.grodnoroads.map.domain.model

import com.google.android.gms.maps.model.LatLng

data class LastLocation(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {
    companion object {
        private val DEFAULT_LOCATION = LatLng(0.0, 0.0)

        val None = LastLocation(
            latLng = DEFAULT_LOCATION,
            bearing = -1f,
            speed = 0
        )
    }
}