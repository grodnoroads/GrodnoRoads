package com.egoriku.grodnoroads.settings.faq.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.coroutines.flow.toCStateFlow
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildFaqComponent(
    componentContext: ComponentContext
): FaqComponent = FaqComponentImpl(componentContext)

internal class FaqComponentImpl(
    componentContext: ComponentContext
) : FaqComponent, KoinComponent, ComponentContext by componentContext {

    private val faqStore: FaqStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: CStateFlow<FaqStore.State>
        get() = faqStore.stateFlow.toCStateFlow()
}