package com.egoriku.grodnoroads.settings.changelog.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.crashlytics.shared.CrashlyticsTracker
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.Message
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ChangelogStoreFactory(
    private val storeFactory: StoreFactory,
    private val changelogRepository: ChangelogRepository,
    private val crashlyticsTracker: CrashlyticsTracker
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    internal fun create(): ChangelogStore =
        object : ChangelogStore, Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dispatch(Message.Loading(true))

                        when (val result = changelogRepository.load()) {
                            is ResultOf.Success -> dispatch(Message.Success(releaseNotes = result.value))
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
                    is Message.Success -> copy(releaseNotes = message.releaseNotes.toImmutableList())
                }
            }
        ) {}
}