package com.egoriku.grodnoroads.guidance.domain.store.quickactions.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
sealed interface QuickActionsPref {
    data class AppTheme(
        val current: Theme = Theme.System,
        val values: ImmutableList<Theme> = Theme.entries.toImmutableList()
    ) : QuickActionsPref
}