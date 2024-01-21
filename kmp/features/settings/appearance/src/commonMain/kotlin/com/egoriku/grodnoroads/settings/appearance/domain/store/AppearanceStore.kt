package com.egoriku.grodnoroads.settings.appearance.domain.store

import androidx.compose.runtime.Stable
import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.*
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.None
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State

interface AppearanceStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class Modify(val preference: AppearancePref) : Intent
        data class Update(val preference: AppearancePref) : Intent
        data object CloseDialog : Intent
    }

    @Stable
    data class State(
        val dialogState: AppearanceDialogState = None,
        val appearanceState: AppearanceState = AppearanceState()
    )

    sealed interface Message {
        data class NewSettings(val appearanceState: AppearanceState) : Message
        data class UpdateLanguage(val appLanguage: AppLanguage) : Message
        data class Dialog(val dialogState: AppearanceDialogState) : Message
    }
}