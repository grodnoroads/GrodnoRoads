package com.egoriku.grodnoroads.guidance.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.guidance.domain.model.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.specialevent.domain.component.specialevent.SpecialEventComponent
import kotlinx.collections.immutable.ImmutableList

@Stable
interface GuidanceComponent {

    val specialEventComponent: SpecialEventComponent

    val lastLocation: CFlow<LastLocation>
    val initialLocation: CFlow<LatLng>

    val appMode: CFlow<AppMode>
    val mapAlertDialog: CFlow<MapAlertDialog>
    val mapConfig: CFlow<MapConfig>
    val mapEvents: CFlow<ImmutableList<MapEvent>>
    val userCount: CFlow<Int>
    val quickActionsState: CFlow<QuickActionsState>

    val alerts: CFlow<ImmutableList<Alert>>

    val speedLimit: CFlow<Int>

    fun openReportFlow(reportDialogFlow: ReportDialogFlow)
    fun reportAction(params: ReportAction.Params)

    fun setLocation(latLng: LatLng)

    fun openChooseLocation(reportType: ReportType)
    fun reportChooseLocation(latLng: LatLng)
    fun setUserMapZoom(zoom: Float)
    fun cancelChooseLocationFlow()

    fun startLocationUpdates()
    fun stopLocationUpdates()
    fun requestCurrentLocation()

    fun updatePreferences(pref: QuickActionsPref)

    fun showMarkerInfoDialog(reports: Reports)
    fun closeDialog()

    sealed interface ReportDialogFlow {
        data class TrafficPolice(val latLng: LatLng) : ReportDialogFlow
        data class RoadIncident(val latLng: LatLng) : ReportDialogFlow
    }
}