package com.egoriku.grodnoroads.settings.appearance.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.coroutines.flow.toCStateFlow
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent.CloseDialog
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildAppearanceComponent(
    componentContext: ComponentContext
): AppearanceComponent = AppearanceComponentImpl(componentContext)

internal class AppearanceComponentImpl(
    componentContext: ComponentContext
) : AppearanceComponent, ComponentContext by componentContext, KoinComponent {

    private val appearanceStore: AppearanceStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: CStateFlow<State>
        get() = appearanceStore.stateFlow.toCStateFlow()

    override fun modify(preference: AppearancePref) {
        appearanceStore.accept(Intent.Modify(preference))
    }

    override fun update(preference: AppearancePref) {
        appearanceStore.accept(Intent.Update(preference))
    }

    override fun closeDialog() {
        appearanceStore.accept(CloseDialog)
    }
}