package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.guidance.domain.model.CameraType
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType
import com.egoriku.grodnoroads.guidance.domain.model.MapInternalConfig.MapInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal fun filterMapEvents(): suspend (
    List<Reports>,
    List<StationaryCamera>,
    List<MobileCamera>,
    List<MediumSpeedCamera>,
    MapInfo
) -> ImmutableList<MapEvent> = { reports, stationary, mobile, mediumSpeed, mapInfo ->
    (reports + stationary + mediumSpeed + mobile).mapNotNull { mapEvent ->

        when (mapEvent) {
            is Camera -> {
                when (mapEvent.cameraType) {
                    CameraType.MobileCamera -> when {
                        mapInfo.showMobileCameras -> mapEvent
                        else -> null
                    }

                    CameraType.StationaryCamera -> when {
                        mapInfo.showStationaryCameras -> mapEvent
                        else -> null
                    }

                    CameraType.MediumSpeedCamera -> when {
                        mapInfo.showMediumSpeedCameras -> mapEvent
                        else -> null
                    }
                }
            }

            is Reports -> {
                when (mapEvent.mapEventType) {
                    MapEventType.RoadIncident -> {
                        when {
                            mapInfo.showRoadIncident -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.TrafficPolice -> {
                        when {
                            mapInfo.showTrafficPolice -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.CarCrash -> {
                        when {
                            mapInfo.showCarCrash -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.TrafficJam -> {
                        when {
                            mapInfo.showTrafficJam -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.WildAnimals -> {
                        when {
                            mapInfo.showWildAnimals -> mapEvent
                            else -> null
                        }
                    }

                    else -> mapEvent
                }
            }
        }
    }.toImmutableList()
}
