package com.egoriku.grodnoroads.setting.alerts.domain.component

import kotlinx.coroutines.flow.Flow

interface AlertsComponent {

    val state: Flow<AlertSettingsState>

    data class AlertSettingsState(
        val alertDistance: AlertDistance = AlertDistance()
    ) {
        data class AlertDistance(val distance: Int = -1)
    }
}