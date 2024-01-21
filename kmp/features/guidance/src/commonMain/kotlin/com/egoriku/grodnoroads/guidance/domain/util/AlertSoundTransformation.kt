package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType.*
import com.egoriku.grodnoroads.guidance.domain.model.MapInternalConfig.AlertsInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

val alertPersistentList = persistentListOf<Alert>()

internal fun alertSoundTransformation(): suspend (ImmutableList<Alert>, AlertsInfo, AppMode) -> ImmutableList<Alert> =
    { alerts, alertInfo, appMode ->
        if (alertInfo.voiceAlertsEnabled && appMode == AppMode.Drive) {
            alerts.mapNotNull { alert ->
                when (alert) {
                    is CameraAlert -> {
                        when (alert.cameraType) {
                            StationaryCamera -> when {
                                alertInfo.notifyStationaryCameras -> alert
                                else -> null
                            }

                            MobileCamera -> when {
                                alertInfo.notifyMobileCameras -> alert
                                else -> null
                            }

                            MediumSpeedCamera -> when {
                                alertInfo.notifyMediumSpeedCameras -> alert
                                else -> null
                            }
                        }
                    }

                    is Alert.IncidentAlert -> {
                        when (alert.mapEventType) {
                            TrafficPolice -> when {
                                alertInfo.notifyTrafficPolice -> alert
                                else -> null
                            }

                            RoadIncident -> when {
                                alertInfo.notifyRoadIncident -> alert
                                else -> null
                            }

                            CarCrash -> when {
                                alertInfo.notifyCarCrash -> alert
                                else -> null
                            }

                            TrafficJam -> when {
                                alertInfo.notifyTrafficJam -> alert
                                else -> null
                            }

                            WildAnimals -> when {
                                alertInfo.notifyWildAnimals -> alert
                                else -> null
                            }

                            else -> null
                        }
                    }
                }
            }.toImmutableList()
        } else {
            alertPersistentList
        }
    }