package com.egoriku.grodnoroads.screen.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.settings.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.screen.settings.faq.domain.component.FaqComponent
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.screen.settings.whatsnew.WhatsNewComponent

interface SettingsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        object Settings : Child()

        data class Appearance(val appearanceComponent: AppearanceComponent) : Child()
        data class Map(val mapSettingsComponent: MapSettingsComponent) : Child()
        data class Alerts(val alertsComponent: AlertsComponent) : Child()
        data class WhatsNew(val whatsNewComponent: WhatsNewComponent) : Child()
        data class NextFeatures(val componentContext: ComponentContext) : Child()
        data class BetaFeatures(val componentContext: ComponentContext) : Child()
        data class FAQ(val faqComponent: FaqComponent) : Child()
    }

    enum class Page {
        Appearance,
        Map,
        Alerts,
        WhatsNew,
        NextFeatures,
        BetaFeatures,
        FAQ
    }

    fun open(page: Page)
    fun onBack()
}