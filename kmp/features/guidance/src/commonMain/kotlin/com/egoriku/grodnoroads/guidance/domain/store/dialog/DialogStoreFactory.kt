package com.egoriku.grodnoroads.guidance.domain.store.dialog

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.guidance.domain.model.MapAlertDialog
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.*
import kotlinx.coroutines.Dispatchers

internal class DialogStoreFactory(
    private val storeFactory: StoreFactory,
    // private val analyticsTracker: AnalyticsTracker
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): DialogStore =
        object : DialogStore, Store<Intent, State, Message> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onIntent<Intent.OpenMarkerInfoDialog> { dialog ->
                    dispatch(
                        Message.OpenDialog(dialog = MapAlertDialog.MarkerInfoDialog(dialog.reports))
                    )
                    // analyticsTracker.trackOpenMarkerInfoDialog()
                }
                onIntent<Intent.OpenReportTrafficPoliceDialog> { data ->
                    dispatch(
                        Message.OpenDialog(
                            dialog = MapAlertDialog.PoliceDialog(
                                currentLatLng = data.latLng
                            )
                        )
                    )
                    // analyticsTracker.trackOpenTrafficPoliceDialog()
                }
                onIntent<Intent.OpenRoadIncidentDialog> { data ->
                    dispatch(
                        Message.OpenDialog(
                            dialog = MapAlertDialog.RoadIncidentDialog(
                                currentLatLng = data.latLng
                            )
                        )
                    )
                    // analyticsTracker.trackOpenRoadIncidentDialog()
                }
                onIntent<Intent.CloseDialog> {
                    dispatch(
                        Message.CloseDialog(dialog = MapAlertDialog.None)
                    )
                }
            },
            reducer = { message: Message ->
                when (message) {
                    is Message.OpenDialog -> copy(mapAlertDialog = message.dialog)
                    is Message.CloseDialog -> copy(mapAlertDialog = message.dialog)
                }
            }
        ) {}
}