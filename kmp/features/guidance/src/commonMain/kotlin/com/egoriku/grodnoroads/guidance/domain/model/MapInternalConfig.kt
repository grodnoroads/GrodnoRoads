package com.egoriku.grodnoroads.guidance.domain.model

import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.Style
import com.egoriku.grodnoroads.shared.persistent.alert.VolumeLevel

internal data class MapInternalConfig(
    val zoomLevelInCity: Float,
    val zoomLevelOutOfCity: Float,
    val alertsDistanceInCity: Int,
    val alertsDistanceOutCity: Int,
    val mapInfo: MapInfo,
    val alertsInfo: AlertsInfo,
    val googleMapStyle: Style,
    val trafficJanOnMap: Boolean,
    val keepScreenOn: Boolean
) {
    internal data class MapInfo(
        val showStationaryCameras: Boolean,
        val showMediumSpeedCameras: Boolean,
        val showMobileCameras: Boolean,
        val showRoadIncident: Boolean,
        val showTrafficPolice: Boolean,
        val showCarCrash: Boolean,
        val showTrafficJam: Boolean,
        val showWildAnimals: Boolean,
    )

    internal data class AlertsInfo(
        val alertsEnabled: Boolean,
        val alertsVolumeLevel: VolumeLevel,
        val voiceAlertsEnabled: Boolean,
        val notifyStationaryCameras: Boolean,
        val notifyMediumSpeedCameras: Boolean,
        val notifyMobileCameras: Boolean,
        val notifyRoadIncident: Boolean,
        val notifyTrafficPolice: Boolean,
        val notifyCarCrash: Boolean,
        val notifyTrafficJam: Boolean,
        val notifyWildAnimals: Boolean,
    )

    companion object {
        val EMPTY = MapInternalConfig(
            zoomLevelInCity = -1f,
            zoomLevelOutOfCity = -1f,
            alertsDistanceInCity = -1,
            alertsDistanceOutCity = -1,
            mapInfo = MapInfo(
                showStationaryCameras = false,
                showMediumSpeedCameras = false,
                showMobileCameras = false,
                showRoadIncident = false,
                showTrafficPolice = false,
                showCarCrash = false,
                showTrafficJam = false,
                showWildAnimals = false
            ),
            alertsInfo = AlertsInfo(
                alertsEnabled = false,
                alertsVolumeLevel = VolumeLevel.Default,
                voiceAlertsEnabled = false,
                notifyStationaryCameras = false,
                notifyMediumSpeedCameras = false,
                notifyMobileCameras = false,
                notifyRoadIncident = false,
                notifyTrafficPolice = false,
                notifyCarCrash = false,
                notifyTrafficJam = false,
                notifyWildAnimals = false
            ),
            googleMapStyle = Style.Minimal,
            trafficJanOnMap = false,
            keepScreenOn = false
        )
    }
}
