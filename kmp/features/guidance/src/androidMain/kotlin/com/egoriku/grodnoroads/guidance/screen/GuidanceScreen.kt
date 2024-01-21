@file:Suppress("NAME_SHADOWING")

package com.egoriku.grodnoroads.guidance.screen

import android.graphics.Point
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.SnackbarHost
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import com.egoriku.grodnoroads.coroutines.onChild
import com.egoriku.grodnoroads.coroutines.reLaunch
import com.egoriku.grodnoroads.foundation.core.alignment.OffsetAlignment
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableFloatState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.guidance.domain.component.GuidanceComponent
import com.egoriku.grodnoroads.guidance.domain.component.GuidanceComponent.ReportDialogFlow
import com.egoriku.grodnoroads.guidance.domain.model.*
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation.Companion.UNKNOWN_LOCATION
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType.*
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.guidance.screen.ui.CameraInfo
import com.egoriku.grodnoroads.guidance.screen.ui.KeepScreenOn
import com.egoriku.grodnoroads.guidance.screen.ui.dialog.IncidentDialog
import com.egoriku.grodnoroads.guidance.screen.ui.dialog.MarkerInfoBottomSheet
import com.egoriku.grodnoroads.guidance.screen.ui.dialog.ReportDialog
import com.egoriku.grodnoroads.guidance.screen.ui.foundation.ModalBottomSheet
import com.egoriku.grodnoroads.guidance.screen.ui.foundation.UsersCount
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize.Large
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize.Small
import com.egoriku.grodnoroads.guidance.screen.ui.google.markers.CameraMarker
import com.egoriku.grodnoroads.guidance.screen.ui.google.markers.NavigationMarker
import com.egoriku.grodnoroads.guidance.screen.ui.google.markers.ReportsMarker
import com.egoriku.grodnoroads.guidance.screen.ui.google.ui.MapOverlayActions
import com.egoriku.grodnoroads.guidance.screen.ui.google.util.rememberMapProperties
import com.egoriku.grodnoroads.guidance.screen.ui.mode.DefaultOverlay
import com.egoriku.grodnoroads.guidance.screen.ui.mode.chooselocation.ChooseLocation
import com.egoriku.grodnoroads.guidance.screen.ui.mode.default.DefaultMode
import com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.DriveMode
import com.egoriku.grodnoroads.guidance.screen.util.MarkerCache
import com.egoriku.grodnoroads.guidance.screen.util.SnackbarMessageBuilder
import com.egoriku.grodnoroads.location.toGmsLatLng
import com.egoriku.grodnoroads.location.toLatLng
import com.egoriku.grodnoroads.maps.compose.GoogleMap
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.api.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.api.ZoomLevelState
import com.egoriku.grodnoroads.maps.compose.impl.onMapScope
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.specialevent.ui.SpecialEventDialog
import com.google.android.gms.maps.Projection
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ui.IconGenerator
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import com.egoriku.grodnoroads.resources.R as R_res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidanceScreen(
    contentPadding: PaddingValues,
    component: GuidanceComponent,
    onBottomNavigationVisibilityChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val snackbarMessageBuilder = remember { SnackbarMessageBuilder(context) }

    val markerCache = koinInject<MarkerCache>()

    val specialEventSlot by component.specialEventComponent.specialEvents.collectAsState()
    specialEventSlot.onChild { dialogComponent ->
        SpecialEventDialog(
            eventType = dialogComponent.eventType,
            onClose = dialogComponent::dismiss
        )
    }

    Surface {
        var cameraInfo by rememberMutableState<MapEvent.Camera?> { null }

        val alerts by component.alerts.collectAsState(initial = persistentListOf())
        val appMode by component.appMode.collectAsState(AppMode.Default)

        val location by component.lastLocation.collectAsState(LastLocation.None)
        val initialLocation by component.initialLocation.collectAsState(UNKNOWN_LOCATION)

        val mapConfig by component.mapConfig.collectAsState(initial = MapConfig.EMPTY)
        val mapEvents by component.mapEvents.collectAsState(initial = persistentListOf())
        val mapAlertDialog by component.mapAlertDialog.collectAsState(initial = MapAlertDialog.None)
        val userCount by component.userCount.collectAsState(initial = 0)
        val speedLimit by component.speedLimit.collectAsState(initial = -1)
        val quickActionsState by component.quickActionsState.collectAsState(initial = QuickActionsState())

        AlertDialogs(
            mapAlertDialog = mapAlertDialog,
            onClose = component::closeDialog,
            reportAction = component::reportAction
        )

        val coroutineScope = rememberCoroutineScope()
        val iconGenerator = remember { IconGenerator(context) }
        var cameraUpdatesJob by rememberMutableState<Job?> { null }

        var isMapLoaded by rememberMutableState { false }
        var isCameraMoving by rememberMutableState { false }
        var isCameraUpdatesEnabled by rememberMutableState { true }
        var idleZoomLevel by rememberMutableFloatState { -1f }

        var isRequestCurrentLocation by rememberMutableState { false }

        var cameraMoveState = remember<CameraMoveState> { CameraMoveState.Idle }

        val overlayVisible by remember {
            derivedStateOf { !isCameraUpdatesEnabled || appMode != AppMode.Drive }
        }

        val markerSize by remember {
            derivedStateOf {
                when {
                    idleZoomLevel == -1f -> Large
                    idleZoomLevel <= 9f -> Small
                    else -> Large
                }
            }
        }

        var projection by rememberMutableState<Projection?> { null }
        var mapUpdater by rememberMutableState<MapUpdater?> { null }

        if (mapConfig != MapConfig.EMPTY && initialLocation != UNKNOWN_LOCATION) {
            val mapProperties = rememberMapProperties(
                mapConfig = mapConfig,
                appMode = appMode,
                isRequestCurrentLocation = isRequestCurrentLocation
            )
            GoogleMap(
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentPadding = contentPadding,
                mapProperties = mapProperties,
                onMapLoaded = { map ->
                    projection = map.projection
                    isMapLoaded = true
                },
                cameraPositionProvider = {
                    cameraPosition {
                        target(initialLocation.toGmsLatLng())
                        zoom(mapConfig.zoomLevel)
                    }
                },
                onMapUpdaterChanged = { mapUpdater = it },
                onProjectionChanged = {
                    if (appMode == AppMode.ChooseLocation) {
                        projection = it
                    }
                },
                onZoomChanged = { zoomLevel ->
                    when (zoomLevel) {
                        is ZoomLevelState.Idle -> {
                            val zoom = zoomLevel.zoom

                            idleZoomLevel = zoom
                            if (appMode == AppMode.ChooseLocation) {
                                component.setUserMapZoom(zoom)
                            }
                        }
                        is ZoomLevelState.Moving -> Unit
                    }
                },
                cameraMoveStateChanged = { state ->
                    cameraMoveState = state

                    when (appMode) {
                        AppMode.ChooseLocation -> {
                            isCameraMoving = cameraMoveState == CameraMoveState.UserGesture
                        }
                        AppMode.Drive -> {
                            if (cameraMoveState == CameraMoveState.UserGesture) {
                                cameraUpdatesJob = coroutineScope.reLaunch(cameraUpdatesJob) {
                                    isCameraUpdatesEnabled = false
                                    delay(5000)
                                    isCameraUpdatesEnabled = true
                                }
                            }
                        }
                        else -> {
                            isCameraUpdatesEnabled = true
                        }
                    }
                }
            )

            LaunchedEffect(location, appMode) {
                val mapUpdater = mapUpdater ?: return@LaunchedEffect
                if (location == LastLocation.None) return@LaunchedEffect

                when (appMode) {
                    AppMode.Drive -> {
                        if (cameraMoveState == CameraMoveState.Animating) return@LaunchedEffect

                        if (mapUpdater.isInitialCameraAnimation()) {
                            mapUpdater.animateCamera(
                                target = location.latLng.toGmsLatLng(),
                                zoom = mapConfig.zoomLevel,
                                bearing = location.bearing
                            )
                        }

                        if (!isCameraUpdatesEnabled || cameraInfo != null || mapAlertDialog != MapAlertDialog.None)
                            return@LaunchedEffect

                        mapUpdater.animateCamera(
                            target = location.latLng.toGmsLatLng(),
                            zoom = mapConfig.zoomLevel,
                            bearing = location.bearing
                        )
                    }
                    else -> {}
                }
            }

            LaunchedEffect(appMode) {
                when (appMode) {
                    AppMode.Default, AppMode.ChooseLocation -> {
                        mapUpdater.onMapScope {
                            resetLastLocation()
                            animateZoom(mapConfig.zoomLevel)
                        }
                    }
                    else -> {}
                }
            }

            LaunchedEffect(location, isRequestCurrentLocation) {
                if (location == LastLocation.None) return@LaunchedEffect

                if (isRequestCurrentLocation) {
                    mapUpdater.onMapScope {
                        animateTarget(
                            target = location.latLng.toGmsLatLng(),
                            zoom = if (appMode == AppMode.Default) 14.5f else null,
                            onFinish = { isRequestCurrentLocation = false },
                            onCancel = { isRequestCurrentLocation = false }
                        )
                    }
                }
            }

            LaunchedEffect(appMode) {
                when (appMode) {
                    AppMode.Default, AppMode.ChooseLocation -> onBottomNavigationVisibilityChange(
                        true
                    )
                    AppMode.Drive -> onBottomNavigationVisibilityChange(false)
                }
            }

            mapUpdater.onMapScope {
                if (appMode == AppMode.Drive && location != LastLocation.None) {
                    val isLight = MaterialTheme.colorScheme.isLight

                    NavigationMarker(
                        tag = if (isLight) "navigation_light" else "navigation_dark",
                        appMode = appMode,
                        position = location.latLng,
                        bearing = location.bearing,
                        icon = {
                            markerCache.getIcon(
                                id = if (isLight) R.drawable.ic_navigation_arrow_dark else R.drawable.ic_navigation_arrow_light,
                            )
                        },
                        rotation = location.bearing
                    )
                }

                mapEvents.forEach { mapEvent ->
                    when (mapEvent) {
                        is MapEvent.Camera -> {
                            when (mapEvent) {
                                is StationaryCamera -> CameraMarker(
                                    position = mapEvent.position,
                                    markerSize = markerSize,
                                    icon = {
                                        val id = when (markerSize) {
                                            Large -> R_res.drawable.ic_map_stationary_camera
                                            Small -> R_res.drawable.ic_map_stationary_camera_small
                                        }
                                        markerCache.getIcon(id)
                                    },
                                    onClick = { cameraInfo = mapEvent }
                                )

                                is MediumSpeedCamera -> {
                                    CameraMarker(
                                        position = mapEvent.position,
                                        markerSize = markerSize,
                                        icon = {
                                            val id = when (markerSize) {
                                                Large -> R_res.drawable.ic_map_medium_speed_camera
                                                Small -> R_res.drawable.ic_map_medium_speed_camera_small
                                            }
                                            markerCache.getIcon(id)
                                        },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                }

                                is MobileCamera -> {
                                    CameraMarker(
                                        position = mapEvent.position,
                                        markerSize = markerSize,
                                        icon = {
                                            val id = when (markerSize) {
                                                Large -> R_res.drawable.ic_map_mobile_camera
                                                Small -> R_res.drawable.ic_map_mobile_camera_small
                                            }
                                            markerCache.getIcon(id)
                                        },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                }
                            }
                        }

                        is MapEvent.Reports -> {
                            ReportsMarker(
                                position = mapEvent.position,
                                markerSize = markerSize,
                                message = mapEvent.markerMessage,
                                iconProvider = {
                                    when (mapEvent.mapEventType) {
                                        TrafficPolice -> markerCache.getIcon(R.drawable.ic_map_police)
                                        RoadIncident -> markerCache.getIcon(R.drawable.ic_map_road_incident)
                                        CarCrash -> markerCache.getIcon(R.drawable.ic_map_car_crash)
                                        TrafficJam -> markerCache.getIcon(R.drawable.ic_map_traffic_jam)
                                        WildAnimals -> markerCache.getIcon(R.drawable.ic_map_wild_animals)
                                        Unsupported -> null
                                    }
                                },
                                iconGenerator = { iconGenerator },
                                onClick = { component.showMarkerInfoDialog(mapEvent) }
                            )
                        }
                    }
                }
            }
        }

        FadeInOutAnimatedVisibility(visible = isMapLoaded) {
            AlwaysKeepScreenOn(mapConfig.keepScreenOn)
            Box(modifier = Modifier.fillMaxSize()) {
                val snackbarState = remember { SnackbarState() }

                AnimatedContent(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(contentPadding),
                    targetState = appMode,
                    label = "app_mode"
                ) { state ->
                    when (state) {
                        AppMode.Default -> {
                            DefaultMode(
                                onLocationRequestStateChanged = {
                                    val message = snackbarMessageBuilder.handleDriveModeRequest(it)

                                    if (message == null) {
                                        component.startLocationUpdates()
                                    } else {
                                        coroutineScope.launch {
                                            snackbarState.show(message)
                                        }
                                    }
                                },
                                report = component::openChooseLocation
                            )
                        }

                        AppMode.Drive -> {
                            DriveMode(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                stopDrive = component::stopLocationUpdates,
                                reportPolice = {
                                    if (location != LastLocation.None) {
                                        component.openReportFlow(
                                            reportDialogFlow = ReportDialogFlow.TrafficPolice(
                                                location.latLng
                                            )
                                        )
                                    }
                                },
                                reportIncident = {
                                    if (location != LastLocation.None) {
                                        component.openReportFlow(
                                            reportDialogFlow = ReportDialogFlow.RoadIncident(
                                                location.latLng
                                            )
                                        )
                                    }
                                }
                            )
                        }

                        AppMode.ChooseLocation -> {
                            ChooseLocation(
                                isCameraMoving = isCameraMoving,
                                onCancel = component::cancelChooseLocationFlow,
                                onLocationSelected = { offset ->
                                    val latLng = projection?.fromScreenLocation(
                                        Point(
                                            /* x = */ offset.x.toInt(),
                                            /* y = */ offset.y.toInt()
                                        )
                                    ) ?: return@ChooseLocation
                                    component.reportChooseLocation(latLng.toLatLng())
                                }
                            )
                        }
                    }
                }
                UsersCount(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 4.dp, end = 16.dp)
                        .padding(contentPadding),
                    count = userCount
                )
                FadeInOutAnimatedVisibility(
                    modifier = Modifier.align(OffsetAlignment(xOffset = 1f, yOffset = 0.45f)),
                    visible = overlayVisible,
                ) {
                    MapOverlayActions(
                        modifier = Modifier.padding(end = 16.dp),
                        zoomIn = { mapUpdater?.zoomIn() },
                        zoomOut = { mapUpdater?.zoomOut() },
                        onLocationRequestStateChanged = {
                            if (appMode == AppMode.Drive) {
                                mapUpdater.onMapScope {
                                    animateCamera(
                                        target = location.latLng.toGmsLatLng(),
                                        zoom = mapConfig.zoomLevel,
                                        bearing = location.bearing
                                    )
                                }
                            } else {
                                val message =
                                    snackbarMessageBuilder.handleCurrentLocationRequest(it)

                                if (message == null) {
                                    isRequestCurrentLocation = true
                                    component.requestCurrentLocation()
                                } else {
                                    coroutineScope.launch {
                                        snackbarState.show(message)
                                    }
                                }
                            }
                        },
                    )
                }
                DefaultOverlay(
                    isOverlayVisible = overlayVisible,
                    isDriveMode = appMode == AppMode.Drive,
                    currentSpeed = location.speed,
                    speedLimit = speedLimit,
                    quickActionsState = quickActionsState,
                    alerts = alerts,
                    onPreferenceChange = component::updatePreferences
                )
                SnackbarHost(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(contentPadding),
                    hostState = snackbarState,
                    paddingValues = PaddingValues(16.dp)
                )
            }
        }

        ModalBottomSheet(
            data = cameraInfo,
            onDismissRequest = { cameraInfo = null },
        ) {
            CameraInfo(it)
        }
    }
}

@Composable
private fun AlwaysKeepScreenOn(enabled: Boolean) {
    KeepScreenOn(enabled)
}

@Composable
private fun AlertDialogs(
    mapAlertDialog: MapAlertDialog,
    onClose: () -> Unit,
    reportAction: (ReportAction.Params) -> Unit
) {
    when (mapAlertDialog) {
        is MapAlertDialog.MarkerInfoDialog -> {
            MarkerInfoBottomSheet(
                reports = mapAlertDialog.reports,
                onClose = onClose
            )
        }

        is MapAlertDialog.PoliceDialog -> {
            ReportDialog(
                onClose = onClose,
                onSend = { mapEvent, shortMessage, message ->
                    reportAction(
                        ReportAction.Params(
                            latLng = mapAlertDialog.currentLatLng,
                            mapEventType = mapEvent,
                            shortMessage = shortMessage,
                            message = message
                        )
                    )
                }
            )
        }

        is MapAlertDialog.RoadIncidentDialog -> {
            IncidentDialog(
                onClose = onClose,
                onSend = { mapEvent, shortMessage, message ->
                    reportAction(
                        ReportAction.Params(
                            latLng = mapAlertDialog.currentLatLng,
                            mapEventType = mapEvent,
                            shortMessage = shortMessage,
                            message = message
                        )
                    )
                }
            )
        }

        is MapAlertDialog.None -> Unit
    }
}