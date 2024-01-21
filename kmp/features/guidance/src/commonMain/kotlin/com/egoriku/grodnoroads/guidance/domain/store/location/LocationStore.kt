package com.egoriku.grodnoroads.guidance.domain.store.location

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation.Companion.UNKNOWN_LOCATION
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStore.*
import com.egoriku.grodnoroads.location.LatLng

interface LocationStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object StartLocationUpdates : Intent
        data object StopLocationUpdates : Intent
        data object RequestCurrentLocation : Intent
        data class SetUserLocation(val latLng: LatLng) : Intent
        data object InvalidateLocation : Intent
    }

    sealed interface Message {
        data class OnNewLocation(val lastLocation: LastLocation) : Message
        data class OnUserLocation(val lastLocation: LastLocation) : Message
        data class OnInitialLocation(val latLng: LatLng) : Message
    }

    sealed interface Label {
        data object None : Label
        data class NewLocation(val latLng: LatLng) : Label
    }

    data class State(
        val lastLocation: LastLocation = LastLocation.None,
        val userLocation: LastLocation = LastLocation.None,
        val initialLocation: LatLng = UNKNOWN_LOCATION
    )
}