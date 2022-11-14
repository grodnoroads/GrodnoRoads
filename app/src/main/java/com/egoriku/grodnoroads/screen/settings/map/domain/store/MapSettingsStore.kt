package com.egoriku.grodnoroads.screen.settings.map.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.Intent
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.StoreState

interface MapSettingsStore : Store<Intent, StoreState, Nothing> {

    sealed interface Intent {
        data class Modify(val preference: MapPref) : Intent
        data class OpenDialog(val preference: MapPref) : Intent
        object CloseDialog : Intent
    }

    data class StoreState(
        val isLoading: Boolean = false,
        val mapSettings: MapSettings = MapSettings(),
        val mapDialogState: MapDialogState = None
    )

    sealed interface Message {
        data class NewSettings(val mapSettings: MapSettings) : Message
        data class Loading(val isLoading: Boolean) : Message
        data class NewDialogState(val mapDialogState: MapDialogState) : Message
    }
}