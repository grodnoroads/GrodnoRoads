package com.egoriku.grodnoroads.map.domain.store.quickactions.model

import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

sealed interface QuickActionsPref {
    data class AppTheme(
        val current: Theme = Theme.System,
        val values: ImmutableList<Theme> = Theme.entries.toImmutableList()
    ) : QuickActionsPref
}