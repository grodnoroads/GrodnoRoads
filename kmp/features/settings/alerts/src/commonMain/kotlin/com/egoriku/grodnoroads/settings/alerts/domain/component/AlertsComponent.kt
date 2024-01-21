package com.egoriku.grodnoroads.settings.alerts.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.*
import com.egoriku.grodnoroads.shared.persistent.Selectable
import com.egoriku.grodnoroads.shared.persistent.alert.DEFAULT_ALERT_DISTANCE_IN_CITY
import com.egoriku.grodnoroads.shared.persistent.alert.DEFAULT_ALERT_DISTANCE_OUTSIDE_CITY
import com.egoriku.grodnoroads.shared.persistent.alert.VolumeLevel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
interface AlertsComponent {

    val state: CFlow<AlertState>

    fun modify(pref: AlertsPref)
    fun reset(pref: AlertsPref)

    @Stable
    data class AlertState(
        val isLoading: Boolean = true,
        val alertSettings: AlertSettings = AlertSettings()
    )

    @Stable
    sealed interface AlertsPref {
        data class AlertAvailability(
            val alertFeatureEnabled: Boolean = false,
            val voiceAlertEnabled: Boolean = false
        ) : AlertsPref

        data class AlertVolumeLevel(
            val current: VolumeLevel = VolumeLevel.Default,
            val values: ImmutableList<VolumeLevel> = VolumeLevel.entries.toImmutableList()
        ) : AlertsPref

        data class AlertRadiusInCity(
            val current: Int = DEFAULT_ALERT_DISTANCE_IN_CITY,
            val min: Int = 200,
            val max: Int = 1000,
            val stepSize: Int = 100
        ) : AlertsPref

        data class AlertRadiusOutCity(
            val current: Int = DEFAULT_ALERT_DISTANCE_OUTSIDE_CITY,
            val min: Int = 600,
            val max: Int = 2500,
            val stepSize: Int = 100
        ) : AlertsPref

        data class StationaryCameras(val isNotify: Boolean = true) : AlertsPref
        data class MediumSpeedCameras(val isNotify: Boolean = true) : AlertsPref
        data class MobileCameras(val isNotify: Boolean = true) : AlertsPref
        data class TrafficPolice(val isNotify: Boolean = true) : AlertsPref
        data class RoadIncident(val isNotify: Boolean = true) : AlertsPref
        data class TrafficJam(val isNotify: Boolean = true) : AlertsPref
        data class WildAnimals(val isNotify: Boolean = true) : AlertsPref
        data class CarCrash(val isNotify: Boolean = true) : AlertsPref
        data class SelectAll(val selectAll: Boolean = true) : AlertsPref
    }

    @Stable
    data class AlertSettings(
        val alertAvailability: AlertAvailability = AlertAvailability(),
        val volumeInfo: VolumeInfo = VolumeInfo(),
        val alertRadius: AlertRadius = AlertRadius(),
        val alertEvents: AlertEvents = AlertEvents()
    ) {
        @Stable
        data class VolumeInfo(val alertVolumeLevel: AlertVolumeLevel = AlertVolumeLevel())

        @Stable
        data class AlertRadius(
            val alertRadiusInCity: AlertRadiusInCity = AlertRadiusInCity(),
            val alertRadiusOutCity: AlertRadiusOutCity = AlertRadiusOutCity(),
        )

        @Stable
        data class AlertEvents(
            val stationaryCameras: StationaryCameras = StationaryCameras(),
            val mediumSpeedCameras: MediumSpeedCameras = MediumSpeedCameras(),
            val mobileCameras: MobileCameras = MobileCameras(),
            val trafficPolice: TrafficPolice = TrafficPolice(),
            val roadIncident: RoadIncident = RoadIncident(),
            val carCrash: CarCrash = CarCrash(),
            val trafficJam: TrafficJam = TrafficJam(),
            val wildAnimals: WildAnimals = WildAnimals(),
            val selectable: Selectable = Selectable.AllEnabled
        )
    }
}