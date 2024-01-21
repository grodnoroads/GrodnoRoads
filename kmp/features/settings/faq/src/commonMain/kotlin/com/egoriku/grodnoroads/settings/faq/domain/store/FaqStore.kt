package com.egoriku.grodnoroads.settings.faq.domain.store

import androidx.compose.runtime.Stable
import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.settings.faq.domain.model.FAQ
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface FaqStore : Store<Nothing, State, Nothing> {

    sealed class Message {
        data class Loading(val isLoading: Boolean) : Message()
        data class Success(val faq: List<FAQ>) : Message()
    }

    @Stable
    data class State(
        val isLoading: Boolean = true,
        val faq: ImmutableList<FAQ> = persistentListOf()
    )
}