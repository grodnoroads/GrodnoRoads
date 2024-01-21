package com.egoriku.grodnoroads.shared.geolocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.core.location.LocationRequestCompat.QUALITY_HIGH_ACCURACY
import com.egoriku.grodnoroads.coroutines.flow.nullable.CNullableMutableStateFlow
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.logger.logD
import com.egoriku.grodnoroads.shared.geolocation.util.MetricUtils.speedToKilometerPerHour
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

class AndroidLocationService(context: Context) : LocationService {
    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    private var lastKnownLocation: LocationInfo? = null

    override val lastLocationFlow = CNullableMutableStateFlow<LocationInfo>(null)

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: return

            lastLocationFlow.tryEmit(
                LocationInfo(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = location.bearing,
                    speed = when {
                        location.hasSpeed() -> speedToKilometerPerHour(location.speed)
                        else -> 0
                    }
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun startLocationUpdates() {
        fusedLocationProvider.removeLocationUpdates(locationCallback)
        fusedLocationProvider.requestLocationUpdates(
            highPrecisionLowIntervalRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun stopLocationUpdates() {
        fusedLocationProvider.removeLocationUpdates(locationCallback)
    }

    override suspend fun getLastKnownLocation(): LocationInfo? {
        if (lastKnownLocation == null) {
            lastKnownLocation = requestLocation().toLocationInfo()
        }

        return lastKnownLocation
    }

    override suspend fun requestCurrentLocation(): LocationInfo? =
        requestLocation().toLocationInfo()

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    private suspend fun requestLocation(): Location? {
        val cancellationTokenSource = CancellationTokenSource()

        return runCatching {
            fusedLocationProvider.getCurrentLocation(
                QUALITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await(cancellationTokenSource)
        }.onFailure {
            logD(it.message.toString())
        }.getOrNull()
    }

    companion object {

        private val highPrecisionLowIntervalRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setMinUpdateDistanceMeters(0f)
                .setMinUpdateIntervalMillis(1000)
                .build()

        private fun Location?.toLocationInfo(): LocationInfo? {
            val location = this
            return when {
                location != null -> LocationInfo(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = location.bearing,
                    speed = 0
                )
                else -> null
            }
        }
    }

}