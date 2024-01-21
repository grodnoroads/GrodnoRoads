package com.egoriku.grodnoroads.settings.alerts.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.audioplayer.Sound
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.common.ui.list.SwitchSettings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertState
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponentPreview
import com.egoriku.grodnoroads.settings.alerts.screen.ui.AlertEventsSection
import com.egoriku.grodnoroads.settings.alerts.screen.ui.AlertRadiusSection
import com.egoriku.grodnoroads.settings.alerts.screen.ui.VoiceLevelSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(
    alertsComponent: AlertsComponent,
    onBack: () -> Unit
) {
    val state by alertsComponent.state.collectAsState(initial = AlertState())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val context = LocalContext.current
    val audioPlayer = remember { AudioPlayer(context) }

    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.release()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_alerts),
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
            ) {
                val settings = state.alertSettings
                val alertAvailability = settings.alertAvailability
                SwitchSettings(
                    stringResId = R.string.alerts_availability,
                    supportingResId = R.string.alerts_availability_description,
                    isChecked = alertAvailability.alertFeatureEnabled,
                    onCheckedChange = { value ->
                        alertsComponent.modify(alertAvailability.copy(alertFeatureEnabled = value))
                    }
                )

                if (alertAvailability.alertFeatureEnabled) {
                    AlertRadiusSection(
                        alertRadius = settings.alertRadius,
                        modify = alertsComponent::modify,
                        reset = alertsComponent::reset
                    )
                    SwitchSettings(
                        stringResId = R.string.alerts_voice_alerts,
                        supportingResId = R.string.alerts_voice_alerts_description,
                        isChecked = settings.alertAvailability.voiceAlertEnabled,
                        onCheckedChange = { value ->
                            alertsComponent.modify(alertAvailability.copy(voiceAlertEnabled = value))
                        }
                    )
                }
                if (alertAvailability.voiceAlertEnabled) {
                    VoiceLevelSection(
                        alertVolumeLevel = settings.volumeInfo.alertVolumeLevel,
                        modify = alertsComponent::modify,
                        playTestSound = { volumeLevel ->
                            audioPlayer.run {
                                 setVolumeLevel(level = volumeLevel.volumeLevel)
                                 setLoudness(volumeLevel.loudness.value)
                                 playSound(sound = Sound.TestAudioLevel)
                             }
                        }
                    )
                    AlertEventsSection(
                        alertEvents = settings.alertEvents,
                        onCheckedChange = alertsComponent::modify
                    )
                }
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun AlertsScreenPreview() = GrodnoRoadsM3ThemePreview {
    AlertsScreen(
        alertsComponent = AlertsComponentPreview(),
        onBack = {}
    )
}