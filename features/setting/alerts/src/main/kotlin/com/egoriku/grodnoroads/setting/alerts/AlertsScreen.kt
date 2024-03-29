package com.egoriku.grodnoroads.setting.alerts

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.audioplayer.Sound
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.uikit.listitem.SwitchListItem
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertState
import com.egoriku.grodnoroads.setting.alerts.ui.AlertEventsSection
import com.egoriku.grodnoroads.setting.alerts.ui.AlertRadiusSection
import com.egoriku.grodnoroads.setting.alerts.ui.VoiceLevelSection

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
        AnimatedContent(
            targetState = state.isLoading,
            transitionSpec = {
                fadeIn(animationSpec = tween(220, delayMillis = 90)) togetherWith
                        fadeOut(animationSpec = tween(90))
            },
            label = "alerts_content"
        ) {
            if (it) {
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
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val settings = state.alertSettings
                    val alertAvailability = settings.alertAvailability
                    SwitchListItem(
                        text = stringResource(R.string.alerts_availability),
                        description = stringResource(R.string.alerts_availability_description),
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
                        SwitchListItem(
                            text = stringResource(R.string.alerts_voice_alerts),
                            description = stringResource(R.string.alerts_voice_alerts_description),
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
}