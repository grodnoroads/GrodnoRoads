package com.egoriku.grodnoroads.location

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.location.util.SphericalUtil
import kotlin.math.roundToInt

@Stable
data class LatLng(val latitude: Double, val longitude: Double)

infix fun LatLng.roundDistanceTo(latLng: LatLng): Int = computeDistance(this, latLng).roundToInt()

fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng =
    SphericalUtil.computeOffset(from, distance, heading)

private fun computeDistance(from: LatLng, to: LatLng) =
    SphericalUtil.computeDistanceBetween(from, to)
