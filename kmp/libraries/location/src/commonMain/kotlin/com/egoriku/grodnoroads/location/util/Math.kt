package com.egoriku.grodnoroads.location.util

import kotlin.math.*

object Math {

    private const val DEGREES_TO_RADIANS = 0.017453292519943295
    private const val RADIANS_TO_DEGREES = 57.29577951308232

    fun toRadians(angdeg: Double): Double = angdeg * DEGREES_TO_RADIANS

    fun toDegrees(angrad: Double): Double = angrad * RADIANS_TO_DEGREES

    fun wrap(n: Double, min: Double, max: Double): Double {
        return when {
            n >= min && n < max -> n
            else -> mod(x = n - min, m = max - min) + min
        }
    }

    fun mod(x: Double, m: Double): Double {
        return (x % m + m) % m
    }

    /**
     * Computes inverse haversine. Has good numerical stability around 0.
     * arcHav(x) == acos(1 - 2 * x) == 2 * asin(sqrt(x)).
     * The argument must be in [0, 1], and the result is positive.
     */
    fun arcHav(x: Double): Double = 2 * asin(sqrt(x))

    /**
     * Returns hav() of distance from (lat1, lng1) to (lat2, lng2) on the unit sphere.
     */
    fun havDistance(lat1: Double, lat2: Double, dLng: Double): Double {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2)
    }

    /**
     * Returns haversine(angle-in-radians).
     * hav(x) == (1 - cos(x)) / 2 == sin(x / 2)^2.
     */
    fun hav(x: Double): Double {
        val sinHalf = sin(x * 0.5)
        return sinHalf * sinHalf
    }

    /**
     * Returns mercator Y corresponding to latitude.
     * See http://en.wikipedia.org/wiki/Mercator_projection .
     */
    fun mercator(lat: Double): Double {
        return ln(tan(lat * 0.5 + PI / 4))
    }
}