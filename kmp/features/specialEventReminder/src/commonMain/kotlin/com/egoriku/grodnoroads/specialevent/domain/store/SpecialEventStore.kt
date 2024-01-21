package com.egoriku.grodnoroads.specialevent.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.store.SpecialEventStore.State

interface SpecialEventStore : Store<Nothing, State, Nothing> {

    data class State(val eventType: EventType? = null)
}