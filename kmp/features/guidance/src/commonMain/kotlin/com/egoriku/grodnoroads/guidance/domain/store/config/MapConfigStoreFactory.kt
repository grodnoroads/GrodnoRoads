package com.egoriku.grodnoroads.guidance.domain.store.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.MapInternalConfig
import com.egoriku.grodnoroads.guidance.domain.model.ReportType
import com.egoriku.grodnoroads.guidance.domain.repository.CityAreasRepository
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent.*
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.StoreState
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStoreFactory.Message.*
import com.egoriku.grodnoroads.guidance.domain.util.CityArea
import com.egoriku.grodnoroads.location.util.PolyUtil
import com.egoriku.grodnoroads.shared.persistent.alert.*
import com.egoriku.grodnoroads.shared.persistent.appearance.keepScreenOn
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.mapZoomInCity
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.mapZoomOutCity
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.*
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.googleMapStyle
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.trafficJamOnMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class MapConfigStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>,
    private val cityAreasRepository: CityAreasRepository
) {

    private sealed interface Message {
        data class OnMapConfigInternal(val mapConfig: MapInternalConfig) : Message
        data class OnZoomLevel(val zoomLevel: Float) : Message
        data class OnAlertRadius(val radius: Int) : Message
        data class OnUserZoomLevel(val userZoomLevel: Float) : Message
        data class ChangeAppMode(val appMode: AppMode) : Message
        data class UpdateReportType(val reportType: ReportType?) : Message
    }

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MapConfigStore =
        object : MapConfigStore, Store<Intent, StoreState, Nothing> by storeFactory.create(
            initialState = StoreState(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { pref ->
                            MapInternalConfig(
                                zoomLevelInCity = pref.mapZoomInCity,
                                zoomLevelOutOfCity = pref.mapZoomOutCity,
                                alertsDistanceInCity = pref.alertsDistanceInCity,
                                alertsDistanceOutCity = pref.alertsDistanceOutsideCity,
                                mapInfo = MapInternalConfig.MapInfo(
                                    showStationaryCameras = pref.isShowStationaryCameras,
                                    showMediumSpeedCameras = pref.isShowMediumSpeedCameras,
                                    showMobileCameras = pref.isShowMobileCameras,
                                    showRoadIncident = pref.isShowRoadIncidents,
                                    showTrafficPolice = pref.isShowTrafficPolice,
                                    showTrafficJam = pref.isShowTrafficJam,
                                    showCarCrash = pref.isShowCarCrash,
                                    showWildAnimals = pref.isShowWildAnimals
                                ),
                                alertsInfo = MapInternalConfig.AlertsInfo(
                                    alertsEnabled = pref.alertsEnabled,
                                    alertsVolumeLevel = pref.alertsVolumeLevel,
                                    voiceAlertsEnabled = pref.alertsVoiceAlertEnabled,
                                    notifyStationaryCameras = pref.isNotifyStationaryCameras,
                                    notifyMediumSpeedCameras = pref.isNotifyMediumSpeedCameras,
                                    notifyMobileCameras = pref.isNotifyMobileCameras,
                                    notifyRoadIncident = pref.isNotifyRoadIncidents,
                                    notifyTrafficPolice = pref.isNotifyTrafficPolice,
                                    notifyTrafficJam = pref.isNotifyTrafficJam,
                                    notifyCarCrash = pref.isNotifyCarCrash,
                                    notifyWildAnimals = pref.isNotifyWildAnimals
                                ),
                                googleMapStyle = pref.googleMapStyle,
                                trafficJanOnMap = pref.trafficJamOnMap,
                                keepScreenOn = pref.keepScreenOn
                            )
                        }
                        .distinctUntilChanged()
                        .onEach { dispatch(OnMapConfigInternal(it)) }
                        .launchIn(this)

                    launch {
                        cityAreasRepository.load()
                    }
                }
                onIntent<CheckLocation> {
                    val latLng = it.latLng

                    val isCityArea = when {
                        PolyUtil.containsLocation(latLng, CityArea.grodno, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.berestovitca, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.skidel, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.ozery, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.porechye, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.volkovysk, false) -> true
                        else -> false
                    }

                    val internalConfig = state.mapInternalConfig
                    val zoomLevel = when {
                        isCityArea -> internalConfig.zoomLevelInCity
                        else -> internalConfig.zoomLevelOutOfCity
                    }
                    dispatch(OnZoomLevel(zoomLevel))

                    val alertRadius = when {
                        isCityArea -> internalConfig.alertsDistanceInCity
                        else -> internalConfig.alertsDistanceOutCity
                    }
                    dispatch(OnAlertRadius(radius = alertRadius))
                }
                onIntent<StartDriveMode> {
                    dispatch(ChangeAppMode(appMode = AppMode.Drive))
                    dispatch(OnZoomLevel(zoomLevel = state.mapInternalConfig.zoomLevelInCity))
                }
                onIntent<StopDriveMode> {
                    dispatch(ChangeAppMode(appMode = AppMode.Default))
                    dispatch(OnZoomLevel(zoomLevel = 12.5f))
                }
                onIntent<ChooseLocation.OpenChooseLocation> {
                    dispatch(ChangeAppMode(appMode = AppMode.ChooseLocation))
                    dispatch(OnZoomLevel(zoomLevel = state.mapInternalConfig.zoomLevelInCity))
                    dispatch(OnUserZoomLevel(userZoomLevel = state.mapInternalConfig.zoomLevelInCity))
                    dispatch(UpdateReportType(reportType = it.reportType))
                }
                onIntent<ChooseLocation.CancelChooseLocation> {
                    dispatch(ChangeAppMode(appMode = AppMode.Default))
                    dispatch(OnZoomLevel(zoomLevel = state.userZoomLevel - 2f))
                    dispatch(UpdateReportType(reportType = null))
                }
                onIntent<ChooseLocation.UserMapZoom> {
                    dispatch(OnUserZoomLevel(it.zoom))
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is OnMapConfigInternal -> copy(mapInternalConfig = message.mapConfig)
                    is OnZoomLevel -> copy(zoomLevel = message.zoomLevel)
                    is ChangeAppMode -> copy(appMode = message.appMode)
                    is UpdateReportType -> copy(reportType = message.reportType)
                    is OnUserZoomLevel -> copy(userZoomLevel = message.userZoomLevel)
                    is OnAlertRadius -> copy(alertRadius = message.radius)
                }
            }) {}
}