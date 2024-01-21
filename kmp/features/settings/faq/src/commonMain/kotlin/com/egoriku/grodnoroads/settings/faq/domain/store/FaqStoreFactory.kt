package com.egoriku.grodnoroads.settings.faq.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.crashlytics.shared.CrashlyticsTracker
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.faq.domain.repository.FaqRepository
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore.Message
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore.State
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FaqStoreFactory(
    private val storeFactory: StoreFactory,
    private val faqRepository: FaqRepository,
    private val crashlyticsTracker: CrashlyticsTracker
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    internal fun create(): FaqStore =
        object : FaqStore, Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        when (val result = faqRepository.load()) {
                            is ResultOf.Success -> dispatch(Message.Success(result.value))
                            is ResultOf.Failure -> crashlyticsTracker.recordException(result.throwable)
                        }
                        dispatch(Message.Loading(false))

                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.Loading -> copy(isLoading = message.isLoading)
                    is Message.Success -> copy(faq = message.faq.toImmutableList())
                }
            }
        ) {}
}