package com.egoriku.grodnoroads.guidance.screen.ui.popup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref.AppTheme
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.persistent.toStringResource

@Composable
fun ActionsContent(
    quickActionsState: QuickActionsState,
    onChanged: (QuickActionsPref) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleSmall,
            text = stringResource(R.string.appearance_app_theme)
        )

        val appTheme = quickActionsState.appTheme

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            appTheme.values.forEach { theme ->
                val selected = appTheme.current == theme
                FilterChip(
                    label = {
                        Text(text = stringResource(theme.toStringResource()))
                    },
                    selected = selected,
                    onClick = {
                        onChanged(appTheme.copy(current = theme))
                    }
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun ActionsContentPreview() = GrodnoRoadsM3ThemePreview {
    var quickActionsState by rememberMutableState { QuickActionsState() }

    ActionsContent(
        quickActionsState = quickActionsState,
        onChanged = {
            quickActionsState = when (it) {
                is AppTheme -> quickActionsState.copy(appTheme = it)
            }
        }
    )
}