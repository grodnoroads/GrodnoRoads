package com.egoriku.grodnoroads.root.domain

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.coroutines.flow.nullable.CNullableStateFlow
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent
import com.egoriku.grodnoroads.onboarding.domain.OnboardingComponent
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme

fun buildRootComponent(
    componentContext: ComponentContext
): RootComponent = RootComponentImpl(componentContext)

@Stable
interface RootComponent {
    val theme: CNullableStateFlow<Theme>
    val childStack: CStateFlow<ChildStack<*, Child>>

    sealed class Child {
        data class Onboarding(val component: OnboardingComponent) : Child()
        data class MainFlow(val component: MainFlowComponent) : Child()
    }
}