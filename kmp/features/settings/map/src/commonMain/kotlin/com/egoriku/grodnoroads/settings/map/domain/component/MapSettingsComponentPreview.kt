package com.egoriku.grodnoroads.settings.map.domain.component

import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.coroutines.flow.stateFlowOf
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettingState

class MapSettingsComponentPreview : MapSettingsComponent {

    override val state: CFlow<MapSettingState>
        get() = stateFlowOf {
            MapSettingState(isLoading = false)
        }

    override fun modify(preference: MapPref) = Unit
    override fun reset(preference: MapPref) = Unit
    override fun openDialog(preference: MapPref) = Unit
    override fun closeDialog() = Unit
}