package com.egoriku.grodnoroads.settings.changelog.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore

@Stable
interface ChangelogComponent {
    val state: CStateFlow<ChangelogStore.State>
}