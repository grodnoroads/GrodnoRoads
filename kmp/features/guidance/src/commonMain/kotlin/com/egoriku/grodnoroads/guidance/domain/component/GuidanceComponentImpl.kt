package com.egoriku.grodnoroads.guidance.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.coroutines.flow.toCFlow
import com.egoriku.grodnoroads.coroutines.coroutineScope
import com.egoriku.grodnoroads.guidance.domain.component.GuidanceComponent.ReportDialogFlow
import com.egoriku.grodnoroads.guidance.domain.model.*
import com.egoriku.grodnoroads.guidance.domain.model.ReportType.RoadIncident
import com.egoriku.grodnoroads.guidance.domain.model.ReportType.TrafficPolice
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent.*
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.Intent.OpenReportTrafficPoliceDialog
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.Intent.OpenRoadIncidentDialog
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStore
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStore.Label
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStore
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStore.QuickActionsIntent
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.guidance.domain.util.alertMessagesTransformation
import com.egoriku.grodnoroads.guidance.domain.util.alertSoundTransformation
import com.egoriku.grodnoroads.guidance.domain.util.filterMapEvents
import com.egoriku.grodnoroads.guidance.domain.util.overSpeedTransformation
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.specialevent.domain.component.specialevent.SpecialEventComponent
import com.egoriku.grodnoroads.specialevent.domain.component.specialevent.buildSpecialEventComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildGuidanceComponent(
    componentContext: ComponentContext
): GuidanceComponent =
    GuidanceComponentImpl(componentContext)

@OptIn(FlowPreview::class)
internal class GuidanceComponentImpl(
    componentContext: ComponentContext
) : GuidanceComponent, ComponentContext by componentContext, KoinComponent {

    private val dialogStore: DialogStore = instanceKeeper.getStore(::get)
    private val locationStore: LocationStore = instanceKeeper.getStore(::get)
    private val mapConfigStore: MapConfigStore = instanceKeeper.getStore(::get)
    private val mapEventsStore: MapEventsStore = instanceKeeper.getStore(::get)
    private val quickActionsStore: QuickActionsStore = instanceKeeper.getStore(::get)

    private val stationary = mapEventsStore.states.map { it.stationaryCameras }
    private val mediumSpeed = mapEventsStore.states.map { it.mediumSpeedCameras }
    private val mobile = mapEventsStore.states.map { it.mobileCameras }
    private val reports = mapEventsStore.states.map { it.reports }

    private val mapInfo = mapConfigStore.states.map { it.mapInternalConfig.mapInfo }
    private val alertInfo = mapConfigStore.states.map { it.mapInternalConfig.alertsInfo }

    private val coroutineScope = coroutineScope(Dispatchers.Main)

    override val specialEventComponent: SpecialEventComponent =
        buildSpecialEventComponent(childContext(key = "special_event"))

    // private val soundUtil by inject<SoundUtil>()

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            locationStore.labels bindTo ::bindLocationLabel
        }

        // TODO: use SharedFlow to remove dependency SoundUtil
        combine(
            flow = alerts,
            flow2 = alertInfo,
            flow3 = appMode,
            transform = alertSoundTransformation()
        ).distinctUntilChanged()
            .debounce(500)
            .onEach { data ->
                data.onEach { alert ->
                    when (alert) {
                        is Alert.CameraAlert -> {
                            /*soundUtil.playCameraLimit(
                                id = alert.id,
                                speedLimit = alert.speedLimit,
                                cameraType = alert.cameraType
                            )*/
                        }

                        is Alert.IncidentAlert -> {
                            /* soundUtil.playIncident(
                                 id = alert.id,
                                 mapEventType = alert.mapEventType
                             )*/
                        }
                    }
                }
            }
            .launchIn(coroutineScope)

        speedLimit
            .distinctUntilChanged()
            .debounce(500)
            .onEach {
                if (it != -1) {
                    // soundUtil.playOverSpeed()
                }
            }
            .launchIn(coroutineScope)

        alertInfo
            .distinctUntilChanged()
            .onEach {
                // soundUtil.setVolume(level = it.alertsVolumeLevel.volumeLevel)
                // soundUtil.setLoudness(loudness = it.alertsVolumeLevel.loudness.value)
            }
            .launchIn(coroutineScope)
    }

    override val appMode: CFlow<AppMode>
        get() = mapConfigStore.states.map { it.appMode }.toCFlow()

    override val mapAlertDialog: CFlow<MapAlertDialog>
        get() = dialogStore.states.map { it.mapAlertDialog }.toCFlow()

    override val userCount: CFlow<Int>
        get() = mapEventsStore.states.map { it.userCount }.toCFlow()

    override val quickActionsState: CFlow<QuickActionsState>
        get() = quickActionsStore.states.toCFlow()

    override val mapConfig: CFlow<MapConfig>
        get() = mapConfigStore.states.map {
            MapConfig(
                zoomLevel = it.zoomLevel,
                googleMapStyle = it.mapInternalConfig.googleMapStyle,
                trafficJanOnMap = it.mapInternalConfig.trafficJanOnMap,
                keepScreenOn = it.mapInternalConfig.keepScreenOn,
                alertRadius = it.alertRadius,
                alertsEnabled = it.mapInternalConfig.alertsInfo.alertsEnabled
            )
        }.toCFlow()

    override val mapEvents: CFlow<ImmutableList<MapEvent>>
        get() = combine(
            flow = reports,
            flow2 = stationary,
            flow3 = mobile,
            flow4 = mediumSpeed,
            flow5 = mapInfo,
            transform = filterMapEvents()
        ).flowOn(Dispatchers.Default)
            .toCFlow()

    override val lastLocation: CFlow<LastLocation>
        get() = locationStore.states.map { it.lastLocation }.toCFlow()

    override val initialLocation: CFlow<LatLng>
        get() = locationStore.states.map { it.initialLocation }.toCFlow()

    override val alerts: CFlow<ImmutableList<Alert>>
        get() = combine(
            flow = mapEvents,
            flow2 = lastLocation,
            flow3 = mapConfig,
            flow4 = appMode,
            transform = alertMessagesTransformation()
        ).flowOn(Dispatchers.Default)
            .toCFlow()

    override val speedLimit: CFlow<Int>
        get() = combine(
            flow = alerts,
            flow2 = lastLocation,
            transform = overSpeedTransformation()
        ).flowOn(Dispatchers.Default)
            .toCFlow()

    override fun startLocationUpdates() {
        locationStore.accept(LocationStore.Intent.StartLocationUpdates)
        mapConfigStore.accept(StartDriveMode)
    }

    override fun stopLocationUpdates() {
        locationStore.accept(LocationStore.Intent.StopLocationUpdates)
        mapConfigStore.accept(StopDriveMode)
    }

    override fun requestCurrentLocation() {
        locationStore.accept(LocationStore.Intent.RequestCurrentLocation)
    }

    override fun setLocation(latLng: LatLng) {
        locationStore.accept(LocationStore.Intent.SetUserLocation(latLng))
    }

    override fun reportAction(params: ReportAction.Params) {
        mapEventsStore.accept(ReportAction(params = params))
        dialogStore.accept(DialogStore.Intent.CloseDialog)

        if (mapConfigStore.state.reportType != null) {
            locationStore.accept(LocationStore.Intent.InvalidateLocation)
            mapConfigStore.accept(ChooseLocation.CancelChooseLocation)
        }
    }

    override fun openChooseLocation(reportType: ReportType) {
        mapConfigStore.accept(ChooseLocation.OpenChooseLocation(reportType))
    }

    override fun cancelChooseLocationFlow() {
        mapConfigStore.accept(ChooseLocation.CancelChooseLocation)
        locationStore.accept(LocationStore.Intent.InvalidateLocation)
    }

    override fun reportChooseLocation(latLng: LatLng) {
        val reportType = mapConfigStore.state.reportType ?: return

        when (reportType) {
            TrafficPolice -> dialogStore.accept(intent = OpenReportTrafficPoliceDialog(latLng))
            RoadIncident -> dialogStore.accept(intent = OpenRoadIncidentDialog(latLng))
        }
    }

    override fun setUserMapZoom(zoom: Float) {
        mapConfigStore.accept(ChooseLocation.UserMapZoom(zoom))
    }

    override fun showMarkerInfoDialog(reports: MapEvent.Reports) =
        dialogStore.accept(DialogStore.Intent.OpenMarkerInfoDialog(reports = reports))

    override fun openReportFlow(reportDialogFlow: ReportDialogFlow) {
        when (reportDialogFlow) {
            is ReportDialogFlow.TrafficPolice -> dialogStore.accept(
                intent = OpenReportTrafficPoliceDialog(reportDialogFlow.latLng)
            )

            is ReportDialogFlow.RoadIncident -> dialogStore.accept(
                intent = OpenRoadIncidentDialog(reportDialogFlow.latLng)
            )
        }
    }

    override fun closeDialog() = dialogStore.accept(DialogStore.Intent.CloseDialog)

    private fun bindLocationLabel(label: Label) = when (label) {
        is Label.NewLocation -> mapConfigStore.accept(CheckLocation(label.latLng))
        else -> Unit
    }

    override fun updatePreferences(pref: QuickActionsPref) {
        quickActionsStore.accept(QuickActionsIntent.Update(pref))
    }
}