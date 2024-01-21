package com.egoriku.grodnoroads.settings.alerts.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.AlertsIntent
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.StoreAlertState

internal interface AlertsStore : Store<AlertsIntent, StoreAlertState, Nothing> {

    data class StoreAlertState(
        val isLoading: Boolean = true,
        val alertSettings: AlertSettings = AlertSettings()
    )

    sealed interface AlertsIntent {
        data class Modify(val pref: AlertsPref) : AlertsIntent
        data class Reset(val pref: AlertsPref) : AlertsIntent
    }

    sealed interface Message {
        data class NewSettings(val alertSettingsState: AlertSettings) : Message
        data class Loading(val isLoading: Boolean) : Message
    }
}