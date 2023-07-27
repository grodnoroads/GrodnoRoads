package com.egoriku.grodnoroads.setting.alerts

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.audioplayer.AudioPlayer
import com.egoriku.grodnoroads.audioplayer.Sound
import com.egoriku.grodnoroads.foundation.list.SwitchSettings
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertState
import com.egoriku.grodnoroads.setting.alerts.ui.AlertEventsSection
import com.egoriku.grodnoroads.setting.alerts.ui.AlertRadiusSection
import com.egoriku.grodnoroads.setting.alerts.ui.VoiceLevelSection
import com.egoriku.grodnoroads.shared.appsettings.types.alert.VolumeLevel

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
                            playSound = { volumeLevel ->
                                audioPlayer.setVolumeLevel(level = volumeLevel.level)
                                audioPlayer.playSound(
                                    sound = when (volumeLevel) {
                                        VolumeLevel.Low -> Sound.TestLowLevel
                                        VolumeLevel.Medium -> Sound.TestMediumLevel
                                        VolumeLevel.High -> Sound.TestHighLevel
                                    }
                                )
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