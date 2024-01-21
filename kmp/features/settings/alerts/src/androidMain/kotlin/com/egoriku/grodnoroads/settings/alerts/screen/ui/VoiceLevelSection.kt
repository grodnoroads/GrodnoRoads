package com.egoriku.grodnoroads.settings.alerts.screen.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.list.ClickableSettingsItem
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.AlertVolumeLevel
import com.egoriku.grodnoroads.shared.persistent.alert.VolumeLevel

@Composable
fun VoiceLevelSection(
    alertVolumeLevel: AlertVolumeLevel,
    modify: (AlertsPref) -> Unit,
    playTestSound: (VolumeLevel) -> Unit
) {
    Column {
        SettingsHeader(
            title = stringResource(id = R.string.alerts_header_volume_level),
            paddingValues = PaddingValues(start = 24.dp, bottom = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            alertVolumeLevel.values.forEach { volumeLevel ->
                val selected = alertVolumeLevel.current == volumeLevel

                FilterChip(
                    selected = selected,
                    onClick = {
                        modify(alertVolumeLevel.copy(current = volumeLevel))
                    },
                    label = {
                        Text(text = volumeLevel.levelName)
                    }
                )
            }
        }
        ClickableSettingsItem(
            imageVector = Icons.Default.PlayCircle,
            text = stringResource(R.string.alerts_play_test_audio),
            onClick = { playTestSound(alertVolumeLevel.current) }
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun VoiceLevelSectionPreview() = GrodnoRoadsM3ThemePreview {
    VoiceLevelSection(
        alertVolumeLevel = AlertVolumeLevel(),
        modify = {},
        playTestSound = {}
    )
}