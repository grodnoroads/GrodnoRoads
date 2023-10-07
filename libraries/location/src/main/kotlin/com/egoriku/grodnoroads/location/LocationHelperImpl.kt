package com.egoriku.grodnoroads.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.core.location.LocationRequestCompat.QUALITY_HIGH_ACCURACY
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.location.MetricUtils.speedToKilometerPerHour
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

internal class LocationHelperImpl(context: Context) : LocationHelper {

    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    private var lastKnownLocation: LocationInfo? = null

    override val lastLocationFlow = MutableStateFlow<LocationInfo?>(null)

    private var lastBearing = 0.0f

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: return

            val directionBearing =
                when {
                    location.hasSpeed() && location.speed > 10 -> {
                        lastBearing = location.bearing
                        lastBearing
                    }
                    else -> lastBearing
                }

            lastLocationFlow.tryEmit(
                LocationInfo(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = directionBearing,
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

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): LocationInfo? {
        if (lastKnownLocation == null) {
            val cancellationTokenSource = CancellationTokenSource()
            val result = runCatching {
                fusedLocationProvider.getCurrentLocation(
                    QUALITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                ).await()
            }.onFailure {
                logD(it.message.toString())
            }

            val location = result.getOrNull()

            lastKnownLocation = when {
                location != null -> LocationInfo(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = location.bearing,
                    speed = 0
                )

                else -> null
            }
        }

        return lastKnownLocation
    }

    companion object {

        private val highPrecisionLowIntervalRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setMinUpdateDistanceMeters(0f)
                .setMinUpdateIntervalMillis(1000)
                .build()
    }
}