package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation

private const val ALLOWED_OVER_SPEED = 7

fun overSpeedTransformation(): suspend (List<Alert>, LastLocation) -> Int =
    { alerts, lastLocation ->
        val cameraEvents = alerts.filterIsInstance<Alert.CameraAlert>()

        if (cameraEvents.isEmpty()) {
            -1
        } else {
            val speedLimit = cameraEvents.first().speedLimit
            val currentSpeed = lastLocation.speed

            when {
                currentSpeed >= speedLimit + ALLOWED_OVER_SPEED -> speedLimit
                else -> -1
            }
        }
    }