package com.egoriku.grodnoroads.appsettings.domain

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.shared.AppBuildConfig
import com.egoriku.grodnoroads.shared.models.Page

fun buildAppSettingsComponent(
    componentContext: ComponentContext,
    onOpen: (Page) -> Unit
): AppSettingsComponent = AppSettingsComponentImpl(
    componentContext = componentContext,
    onOpen = onOpen
)

internal class AppSettingsComponentImpl(
    componentContext: ComponentContext,
    private val onOpen: (Page) -> Unit
) : AppSettingsComponent, ComponentContext by componentContext {

    override val appVersion = AppBuildConfig.versionName

    override fun open(page: Page) = onOpen(page)
}