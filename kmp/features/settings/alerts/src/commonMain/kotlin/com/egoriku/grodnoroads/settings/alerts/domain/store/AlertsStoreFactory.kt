package com.egoriku.grodnoroads.settings.alerts.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.*
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.*
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.*
import com.egoriku.grodnoroads.shared.persistent.Selectable
import com.egoriku.grodnoroads.shared.persistent.alert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class AlertsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AlertsStore = object : AlertsStore,
        Store<AlertsIntent, StoreAlertState, Nothing> by storeFactory.create(
            initialState = StoreAlertState(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { pref ->
                            val notifyStationaryCameras = pref.isNotifyStationaryCameras
                            val notifyMediumSpeedCameras = pref.isNotifyMediumSpeedCameras
                            val notifyMobileCameras = pref.isNotifyMobileCameras
                            val notifyTrafficPolice = pref.isNotifyTrafficPolice
                            val notifyRoadIncidents = pref.isNotifyRoadIncidents
                            val notifyCarCrash = pref.isNotifyCarCrash
                            val notifyTrafficJam = pref.isNotifyTrafficJam
                            val notifyWildAnimals = pref.isNotifyWildAnimals

                            val isShowMarkers = listOf(
                                notifyStationaryCameras, notifyMediumSpeedCameras,
                                notifyMobileCameras, notifyTrafficPolice,
                                notifyRoadIncidents, notifyCarCrash,
                                notifyTrafficJam, notifyWildAnimals
                            )

                            val isAllMarkersDisabled = isShowMarkers.none { it }
                            val isAllMarkersEnabled = isShowMarkers.all { it }
                            val selectable = when {
                                isAllMarkersDisabled -> Selectable.AllDisabled
                                isAllMarkersEnabled -> Selectable.AllEnabled
                                else -> Selectable.Mixed
                            }

                            AlertSettings(
                                alertAvailability = AlertAvailability(
                                    alertFeatureEnabled = pref.alertsEnabled,
                                    voiceAlertEnabled = pref.alertsVoiceAlertEnabled
                                ),
                                volumeInfo = VolumeInfo(
                                    alertVolumeLevel = AlertVolumeLevel(
                                        current = pref.alertsVolumeLevel
                                    )
                                ),
                                alertRadius = AlertRadius(
                                    alertRadiusInCity = AlertRadiusInCity(
                                        current = pref.alertsDistanceInCity
                                    ),
                                    alertRadiusOutCity = AlertRadiusOutCity(
                                        current = pref.alertsDistanceOutsideCity
                                    )
                                ),
                                alertEvents = AlertEvents(
                                    stationaryCameras = StationaryCameras(isNotify = notifyStationaryCameras),
                                    mediumSpeedCameras = MediumSpeedCameras(isNotify = notifyMediumSpeedCameras),
                                    mobileCameras = MobileCameras(isNotify = notifyMobileCameras),
                                    trafficPolice = TrafficPolice(isNotify = notifyTrafficPolice),
                                    roadIncident = RoadIncident(isNotify = notifyRoadIncidents),
                                    carCrash = CarCrash(isNotify = notifyCarCrash),
                                    trafficJam = TrafficJam(isNotify = notifyTrafficJam),
                                    wildAnimals = WildAnimals(isNotify = notifyWildAnimals),
                                    selectable = selectable
                                )
                            )
                        }
                        .distinctUntilChanged()
                        .onEach {
                            dispatch(Message.NewSettings(it))
                            dispatch(Message.Loading(false))
                        }
                        .launchIn(this)
                }
                onIntent<AlertsIntent.Modify> {
                    launch {
                        dataStore.edit {
                            when (val pref = it.pref) {
                                is AlertAvailability -> {
                                    if (pref.alertFeatureEnabled) {
                                        updateAlertsAvailability(pref.alertFeatureEnabled)
                                        updateAlertsVoiceAlertAvailability(pref.voiceAlertEnabled)
                                    } else {
                                        updateAlertsAvailability(false)
                                        updateAlertsVoiceAlertAvailability(false)
                                    }
                                }

                                is AlertVolumeLevel -> updateAlertsVolumeInfo(pref.current.levelName)

                                is AlertRadiusInCity -> updateAlertsDistanceInCity(pref.current)
                                is AlertRadiusOutCity -> updateAlertsDistanceOutsideCity(pref.current)

                                is CarCrash -> updateNotifyCarCrash(pref.isNotify)
                                is MediumSpeedCameras -> updateNotifyMediumSpeedCameras(pref.isNotify)
                                is MobileCameras -> updateNotifyMobileCameras(pref.isNotify)
                                is RoadIncident -> updateNotifyRoadIncidents(pref.isNotify)
                                is StationaryCameras -> updateNotifyStationaryCameras(pref.isNotify)
                                is TrafficJam -> updateNotifyTrafficJam(pref.isNotify)
                                is TrafficPolice -> updateNotifyTrafficPolice(pref.isNotify)
                                is WildAnimals -> updateNotifyWildAnimals(pref.isNotify)
                                is SelectAll -> {
                                    updateNotifyCarCrash(pref.selectAll)
                                    updateNotifyMediumSpeedCameras(pref.selectAll)
                                    updateNotifyMobileCameras(pref.selectAll)
                                    updateNotifyRoadIncidents(pref.selectAll)
                                    updateNotifyStationaryCameras(pref.selectAll)
                                    updateNotifyTrafficJam(pref.selectAll)
                                    updateNotifyTrafficPolice(pref.selectAll)
                                    updateNotifyWildAnimals(pref.selectAll)
                                }
                            }
                        }
                    }
                }
                onIntent<AlertsIntent.Reset> {
                    launch {
                        dataStore.edit {
                            when (it.pref) {
                                is AlertRadiusInCity -> updateAlertsDistanceInCity(
                                    DEFAULT_ALERT_DISTANCE_IN_CITY
                                )

                                is AlertRadiusOutCity -> updateAlertsDistanceOutsideCity(
                                    DEFAULT_ALERT_DISTANCE_OUTSIDE_CITY
                                )

                                else -> Unit
                            }
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewSettings -> copy(alertSettings = message.alertSettingsState)
                    is Message.Loading -> copy(isLoading = message.isLoading)
                }
            }
        ) {}
}