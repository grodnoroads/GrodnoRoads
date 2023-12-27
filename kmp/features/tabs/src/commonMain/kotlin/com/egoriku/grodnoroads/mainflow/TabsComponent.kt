package com.egoriku.grodnoroads.mainflow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponent
import com.egoriku.grodnoroads.guidance.domain.component.GuidanceComponent

interface TabsComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onSelectTab(index: Int)

    sealed class Child(val index: Int) {
        data class Guidance(val component: GuidanceComponent) : Child(index = 0)
        data class AppSettings(val component: AppSettingsComponent) : Child(index = 1)
    }
}