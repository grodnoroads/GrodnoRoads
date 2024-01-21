package com.egoriku.grodnoroads.guidance.domain.store.dialog

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.guidance.domain.model.MapAlertDialog
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.*
import com.egoriku.grodnoroads.location.LatLng

interface DialogStore : Store<Intent, State, Message> {

    sealed interface Intent {
        data class OpenMarkerInfoDialog(val reports: MapEvent.Reports) : Intent
        data class OpenReportTrafficPoliceDialog(val latLng: LatLng) : Intent
        data class OpenRoadIncidentDialog(val latLng: LatLng) : Intent
        data object CloseDialog : Intent
    }

    sealed interface Message {
        data class OpenDialog(val dialog: MapAlertDialog) : Message
        data class CloseDialog(val dialog: MapAlertDialog.None) : Message
    }

    data class State(
        val mapAlertDialog: MapAlertDialog = MapAlertDialog.None
    )
}