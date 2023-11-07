package com.egoriku.grodnoroads.map.domain.component

import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.maps.core.StableLatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val lastLocation: Flow<LastLocation>
    val initialLocation : Flow<StableLatLng>

    val appMode: Flow<AppMode>
    val mapAlertDialog: Flow<MapAlertDialog>
    val mapConfig: Flow<MapConfig>
    val mapEvents: Flow<ImmutableList<MapEvent>>
    val userCount: Flow<Int>
    val quickActionsState: Flow<QuickActionsState>

    val labels: Flow<LocationStore.Label>

    val alerts: Flow<ImmutableList<Alert>>

    val speedLimit: Flow<Int>

    fun openReportFlow(reportDialogFlow: ReportDialogFlow)
    fun reportAction(params: ReportAction.Params)

    fun setLocation(latLng: StableLatLng)

    fun openChooseLocation(reportType: ReportType)
    fun reportChooseLocation(latLng: StableLatLng)
    fun setUserMapZoom(zoom: Float)
    fun cancelChooseLocationFlow()

    fun startLocationUpdates()
    fun stopLocationUpdates()

    fun onLocationDisabled()

    fun updatePreferences(pref: QuickActionsPref)

    fun showMarkerInfoDialog(reports: Reports)
    fun closeDialog()

    sealed interface ReportDialogFlow {
        data class TrafficPolice(val latLng: StableLatLng) : ReportDialogFlow
        data class RoadIncident(val latLng: StableLatLng) : ReportDialogFlow
    }
}