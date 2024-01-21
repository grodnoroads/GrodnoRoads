package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.coroutines.flow.toCStateFlow
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildChangelogComponent(
    componentContext: ComponentContext
): ChangelogComponent = ChangelogComponentImpl(componentContext)

internal class ChangelogComponentImpl(
    componentContext: ComponentContext
) : ChangelogComponent, KoinComponent, ComponentContext by componentContext {

    private val changelogStore: ChangelogStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: CStateFlow<ChangelogStore.State>
        get() = changelogStore.stateFlow.toCStateFlow()
}