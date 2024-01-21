package com.egoriku.grodnoroads.settings.changelog.domain.store

import androidx.compose.runtime.Stable
import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.settings.changelog.domain.model.ReleaseNotes
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

interface ChangelogStore : Store<Nothing, State, Nothing> {

    sealed class Message {
        data class Loading(val isLoading: Boolean) : Message()
        data class Success(val releaseNotes: List<ReleaseNotes>) : Message()
    }

    @Stable
    data class State(
        val isLoading: Boolean = true,
        val releaseNotes: ImmutableList<ReleaseNotes> = persistentListOf()
    )
}