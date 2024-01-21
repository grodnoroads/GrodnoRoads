package com.egoriku.grodnoroads.settings.alerts.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.coroutines.flow.toCFlow
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertState
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.AlertsIntent
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildAlertsComponent(
    componentContext: ComponentContext
): AlertsComponent = AlertsComponentImpl(componentContext)

internal class AlertsComponentImpl(
    componentContext: ComponentContext
) : AlertsComponent, ComponentContext by componentContext, KoinComponent {

    private val alertsStore: AlertsStore = instanceKeeper.getStore(::get)

    override val state: CFlow<AlertState>
        get() = alertsStore.states.map {
            AlertState(
                isLoading = it.isLoading,
                alertSettings = it.alertSettings
            )
        }.toCFlow()

    override fun modify(pref: AlertsComponent.AlertsPref) {
        alertsStore.accept(AlertsIntent.Modify(pref))
    }

    override fun reset(pref: AlertsComponent.AlertsPref) {
        alertsStore.accept(AlertsIntent.Reset(pref))
    }
}