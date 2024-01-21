package com.egoriku.grodnoroads.guidance.domain.store.quickactions

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.QuickActionsStore.QuickActionsIntent
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsState

interface QuickActionsStore : Store<QuickActionsIntent, QuickActionsState, Nothing> {
    sealed interface QuickActionsIntent {
        data class Update(val preference: QuickActionsPref) : QuickActionsIntent
    }

    sealed interface QuickActionsMessage {
        data class NewSettings(val appearanceState: QuickActionsState) : QuickActionsMessage
    }
}