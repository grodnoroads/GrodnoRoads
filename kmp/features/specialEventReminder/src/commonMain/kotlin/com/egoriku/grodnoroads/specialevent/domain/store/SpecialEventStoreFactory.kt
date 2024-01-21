package com.egoriku.grodnoroads.specialevent.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStore.State
import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStoreFactory.Message.EventTypeChanged
import com.egoriku.grodnoroads.specialevent.domain.util.SpecialEventDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpecialEventStoreFactory(private val storeFactory: StoreFactory) {

    private sealed interface Message {
        data class EventTypeChanged(val eventType: EventType) : Message
    }

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): SpecialEventStore =
        object : SpecialEventStore,
            Store<Nothing, State, Nothing> by storeFactory.create(
                initialState = State(),
                executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                    onAction<Unit> {
                        launch {
                            val eventType = SpecialEventDispatcher.calculateType()
                            if (eventType != null) {
                                dispatch(EventTypeChanged(eventType))
                            }
                        }
                    }
                },
                bootstrapper = SimpleBootstrapper(Unit),
                reducer = { message: Message ->
                    when (message) {
                        is EventTypeChanged -> copy(eventType = message.eventType)
                    }
                }
            ) {}
}