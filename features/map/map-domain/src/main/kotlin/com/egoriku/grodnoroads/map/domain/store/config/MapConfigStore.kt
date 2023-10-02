package com.egoriku.grodnoroads.map.domain.store.config

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.MapInternalConfig
import com.egoriku.grodnoroads.map.domain.model.ReportType
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.Intent
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.StoreState
import com.egoriku.grodnoroads.maps.core.StableLatLng

internal interface MapConfigStore : Store<Intent, StoreState, Nothing> {

    sealed interface Intent {
        data class CheckLocation(val latLng: StableLatLng) : Intent
        data object StartDriveMode : Intent
        data object StopDriveMode : Intent

        sealed interface ChooseLocation {
            data class OpenChooseLocation(val reportType: ReportType) : Intent
            data class UserMapZoom(val zoom: Float) : Intent
            data object CancelChooseLocation : Intent
        }
    }

    data class StoreState(
        val mapInternalConfig: MapInternalConfig = MapInternalConfig.EMPTY,
        val zoomLevel: Float = 12.5f,
        val userZoomLevel: Float = 0f,
        val alertRadius: Int = 0,
        val appMode: AppMode = AppMode.Default,
        val reportType: ReportType? = null
    )
}