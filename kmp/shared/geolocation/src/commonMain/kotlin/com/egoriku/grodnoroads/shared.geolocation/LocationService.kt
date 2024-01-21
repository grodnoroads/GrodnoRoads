package com.egoriku.grodnoroads.shared.geolocation

import com.egoriku.grodnoroads.coroutines.flow.nullable.CNullableStateFlow

interface LocationService {

    val lastLocationFlow: CNullableStateFlow<LocationInfo>

    suspend fun getLastKnownLocation(): LocationInfo?

    suspend fun requestCurrentLocation(): LocationInfo?

    fun startLocationUpdates()
    fun stopLocationUpdates()
}