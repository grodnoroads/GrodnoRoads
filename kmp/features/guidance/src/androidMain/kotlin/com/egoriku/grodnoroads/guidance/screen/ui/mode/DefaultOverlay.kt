package com.egoriku.grodnoroads.guidance.screen.ui.mode

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.domain.model.Source
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.Alerts
import com.egoriku.grodnoroads.guidance.screen.ui.popup.ActionsContent
import com.egoriku.grodnoroads.guidance.screen.ui.popup.QuickActionsPopup
import com.egoriku.grodnoroads.uuid.Uuid
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay

@Composable
fun DefaultOverlay(
    isOverlayVisible: Boolean,
    isDriveMode: Boolean,
    currentSpeed: Int,
    speedLimit: Int,
    quickActionsState: QuickActionsState,
    alerts: ImmutableList<Alert>,
    onPreferenceChange: (QuickActionsPref) -> Unit,
) {
    var quickActionsVisible by rememberMutableState { false }
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isDriveMode) {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy((-8).dp)
                ) {
                    CarSpeed(speed = currentSpeed)
                    if (speedLimit != -1) {
                        SpeedLimit(limit = speedLimit)
                    }
                }
                Alerts(alerts = alerts)
            }
        }
        FadeInOutAnimatedVisibility(visible = isOverlayVisible) {
            QuickActionsPopup(
                modifier = Modifier.statusBarsPadding(),
                opened = quickActionsVisible,
                onExpand = { quickActionsVisible = true },
                onClosed = { quickActionsVisible = false },
            ) {
                ActionsContent(
                    quickActionsState = quickActionsState,
                    onChanged = {
                        quickActionsVisible = false
                        onPreferenceChange(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun CarSpeed(modifier: Modifier = Modifier, speed: Int) {
    Card(
        modifier = modifier.size(64.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = CircleShape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = speed.toString(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun SpeedLimit(limit: Int) {
    val circles = listOf(
        remember { Animatable(initialValue = 0.4f) },
        remember { Animatable(initialValue = 0.4f) },
        remember { Animatable(initialValue = 0.4f) },
    )

    val animationDelay = 1500

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            delay(timeMillis = (animationDelay / 3L) * (index + 1))

            animatable.animateTo(
                targetValue = 0.8f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Box(modifier = Modifier.size(48.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            circles.forEach { animatable ->
                drawCircle(
                    radius = size.minDimension * animatable.value,
                    alpha = 0.8f - animatable.value,
                    color = Color.Red
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.White)
                .border(width = 3.dp, color = Color.Red, shape = CircleShape),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                text = limit.toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@GrodnoRoadsDarkLightPreview
@Composable
private fun DefaultOverlayPreview() = GrodnoRoadsM3ThemePreview {
    var limit by rememberMutableState { -1 }

    Box {
        DefaultOverlay(
            isDriveMode = true,
            currentSpeed = 120,
            speedLimit = limit,
            isOverlayVisible = true,
            quickActionsState = QuickActionsState(),
            alerts = persistentListOf(
                IncidentAlert(
                    id = Uuid.randomUUID(),
                    mapEventType = MapEventType.TrafficPolice,
                    distance = 1,
                    messages = persistentListOf(
                        MessageItem(
                            message = "Славинского Беларуснефть на скорость",
                            source = Source.Viber
                        )
                    )
                ),
                IncidentAlert(
                    id = Uuid.randomUUID(),
                    mapEventType = MapEventType.CarCrash,
                    distance = 120,
                    messages = persistentListOf(
                        MessageItem(
                            message = "Славинского ДТП",
                            source = Source.Viber
                        )
                    )
                )
            )
        ) {}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { limit = 50 }) {
                Text(text = "Set limit")
            }
            Button(onClick = { limit = -1 }) {
                Text(text = "Reset limit")
            }
        }
    }
}
