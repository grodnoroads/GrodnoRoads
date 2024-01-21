package com.egoriku.grodnoroads.guidance.domain.store.quickactions.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref.AppTheme

@Stable
data class QuickActionsState(
    val appTheme: AppTheme = AppTheme()
)