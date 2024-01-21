package com.egoriku.grodnoroads.settings.map.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.coroutines.flow.toCFlow
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettingState
import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStore
import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStore.Intent
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildMapSettingsComponent(
    componentContext: ComponentContext
): MapSettingsComponent = MapSettingsComponentImpl(componentContext)

internal class MapSettingsComponentImpl(
    componentContext: ComponentContext
) : MapSettingsComponent, ComponentContext by componentContext, KoinComponent {

    private val mapSettingsStore: MapSettingsStore = instanceKeeper.getStore(::get)

    override val state: CFlow<MapSettingState>
        get() = mapSettingsStore.states.map { storeState ->
            MapSettingState(
                isLoading = storeState.isLoading,
                mapSettings = storeState.mapSettings,
                mapDialogState = storeState.mapDialogState,
            )
        }.toCFlow()

    override fun modify(preference: MapPref) {
        mapSettingsStore.accept(Intent.Modify(preference))
    }

    override fun reset(preference: MapPref) {
        mapSettingsStore.accept(Intent.Reset(preference))
    }

    override fun openDialog(preference: MapPref) {
        mapSettingsStore.accept(Intent.OpenDialog(preference))
    }

    override fun closeDialog() {
        mapSettingsStore.accept(Intent.CloseDialog)
    }
}