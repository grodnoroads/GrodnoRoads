package com.egoriku.grodnoroads.map.domain.store.dialog

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.analytics.AnalyticsTracker
import com.egoriku.grodnoroads.map.domain.model.MapBottomSheet
import com.egoriku.grodnoroads.map.domain.store.dialog.DialogStore.*
import kotlinx.coroutines.Dispatchers

internal class DialogStoreFactory(
    private val storeFactory: StoreFactory,
    private val analyticsTracker: AnalyticsTracker
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): DialogStore =
        object : DialogStore, Store<Intent, State, Message> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onIntent<Intent.OpenMarkerInfoDialog> { dialog ->
                    dispatch(
                        Message.OpenDialog(dialog = MapBottomSheet.MarkerInfo(dialog.reports))
                    )
                    analyticsTracker.trackOpenMarkerInfoDialog()
                }
                onIntent<Intent.OpenQuickSettings> {
                    dispatch(Message.OpenDialog(dialog = MapBottomSheet.QuickSettings))
                }
                onIntent<Intent.CloseDialog> {
                    dispatch(Message.CloseDialog(dialog = MapBottomSheet.None))
                }
            },
            reducer = { message: Message ->
                when (message) {
                    is Message.OpenDialog -> copy(mapBottomSheet = message.dialog)
                    is Message.CloseDialog -> copy(mapBottomSheet = message.dialog)
                }
            }
        ) {}
}