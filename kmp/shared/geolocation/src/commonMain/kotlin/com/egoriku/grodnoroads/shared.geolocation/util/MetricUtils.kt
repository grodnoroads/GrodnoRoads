package com.egoriku.grodnoroads.shared.geolocation.util

internal object MetricUtils {

    private const val KM_PER_HOUR_MULTIPLY = 3.6

    fun speedToKilometerPerHour(speedInMetersPerSeconds: Float) =
        (speedInMetersPerSeconds * KM_PER_HOUR_MULTIPLY).toInt()
}