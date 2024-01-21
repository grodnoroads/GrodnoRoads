package com.egoriku.grodnoroads.mainflow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.egoriku.grodnoroads.appsettings.domain.buildAppSettingsComponent
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.coroutines.flow.toCStateFlow
import com.egoriku.grodnoroads.coroutines.toStateFlow
import com.egoriku.grodnoroads.guidance.domain.component.buildGuidanceComponent
import com.egoriku.grodnoroads.mainflow.TabsComponent.Child
import com.egoriku.grodnoroads.shared.models.Page
import kotlinx.serialization.Serializable

fun buildTabComponent(
    componentContext: ComponentContext,
    onOpenPage: (Page) -> Unit
): TabsComponent = TabsComponentImpl(
    componentContext = componentContext,
    onOpenPage = onOpenPage
)

internal class TabsComponentImpl(
    componentContext: ComponentContext,
    private val onOpenPage: (Page) -> Unit
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Guidance,
        handleBackButton = true,
        key = "TabStack",
        childFactory = ::processChild
    )

    override val childStack: CStateFlow<ChildStack<*, Child>> = stack.toStateFlow().toCStateFlow()

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Guidance -> Child.Guidance(buildGuidanceComponent(componentContext))
        is Config.AppSettings -> Child.AppSettings(
            buildAppSettingsComponent(
                componentContext = componentContext,
                onOpen = onOpenPage
            )
        )
    }

    override fun onSelectTab(index: Int) {
        if (index == 0) {
            navigation.replaceAll(Config.Guidance)
        } else {
            navigation.bringToFront(Config.AppSettings)
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Guidance : Config

        @Serializable
        data object AppSettings : Config
    }
}