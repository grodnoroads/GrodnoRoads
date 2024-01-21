package com.egoriku.grodnoroads.guidance.domain.store.mapevents

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore.Intent
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore.State
import com.egoriku.grodnoroads.location.LatLng

interface MapEventsStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class ReportAction(val params: Params) : Intent {
            data class Params(
                val latLng: LatLng,
                val mapEventType: MapEventType,
                val shortMessage: String,
                val message: String
            )
        }
    }

    sealed interface Message {
        data class OnStationary(val data: List<StationaryCamera>) : Message
        data class OnMediumSpeed(val data: List<MediumSpeedCamera>) : Message
        data class OnNewReports(val data: List<Reports>) : Message
        data class OnMobileCamera(val data: List<MobileCamera>) : Message
        data class OnUserCount(val data: Int) : Message
    }

    data class State(
        val stationaryCameras: List<StationaryCamera> = emptyList(),
        val mediumSpeedCameras: List<MediumSpeedCamera> = emptyList(),
        val mobileCameras: List<MobileCamera> = emptyList(),
        val reports: List<Reports> = emptyList(),
        val userCount: Int = 0
    )
}