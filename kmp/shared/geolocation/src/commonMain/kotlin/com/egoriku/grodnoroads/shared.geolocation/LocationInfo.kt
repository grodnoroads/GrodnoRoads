package com.egoriku.grodnoroads.shared.geolocation

import com.egoriku.grodnoroads.location.LatLng

data class LocationInfo(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
)
