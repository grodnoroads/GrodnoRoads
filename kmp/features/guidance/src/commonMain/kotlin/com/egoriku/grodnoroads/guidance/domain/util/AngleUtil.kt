package com.egoriku.grodnoroads.guidance.domain.util

import kotlin.math.abs

private const val THRESHOLD = 30f

fun isAngleInRange(cameraAngle: Float, bearing: Float, bidirectional: Boolean): Boolean {
    return if (bidirectional) {
        isOppositeAngle(cameraAngle, bearing) || isOppositeAngle(cameraAngle.opposite(), bearing)
    } else {
        isOppositeAngle(cameraAngle, bearing)
    }
}

private fun isOppositeAngle(a: Float, b: Float): Boolean {
    return abs(a - b) in 180 - THRESHOLD..180 + THRESHOLD
}

private fun Float.opposite(): Float = (this + 180f) % 360f