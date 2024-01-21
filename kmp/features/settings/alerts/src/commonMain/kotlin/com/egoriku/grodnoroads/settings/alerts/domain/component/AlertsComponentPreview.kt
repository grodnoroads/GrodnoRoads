package com.egoriku.grodnoroads.settings.alerts.domain.component

import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.coroutines.flow.flowOf
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertState

class AlertsComponentPreview : AlertsComponent {

    override val state: CFlow<AlertState>
        get() = flowOf(AlertState(isLoading = false))

    override fun modify(pref: AlertsComponent.AlertsPref) = Unit
    override fun reset(pref: AlertsComponent.AlertsPref) = Unit
}