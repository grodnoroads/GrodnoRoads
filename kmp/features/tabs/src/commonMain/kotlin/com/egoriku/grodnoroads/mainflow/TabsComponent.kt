package com.egoriku.grodnoroads.mainflow

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponent
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.guidance.domain.component.GuidanceComponent

@Stable
interface TabsComponent {

    val childStack: CStateFlow<ChildStack<*, Child>>

    fun onSelectTab(index: Int)

    sealed class Child(val index: Int) {
        data class Guidance(val component: GuidanceComponent) : Child(index = 0)
        data class AppSettings(val component: AppSettingsComponent) : Child(index = 1)
    }
}