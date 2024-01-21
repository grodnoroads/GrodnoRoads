package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType.*
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.domain.model.Source
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.uuid.Uuid
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Alerts(
    modifier: Modifier = Modifier,
    alerts: ImmutableList<Alert>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
    ) {
        items(items = alerts, key = { it.id }) { alert ->
            when (alert) {
                is IncidentAlert -> {
                    val title = when (alert.mapEventType) {
                        RoadIncident -> stringResource(R.string.alerts_incident)
                        TrafficPolice -> stringResource(R.string.alerts_traffic_police)
                        CarCrash -> stringResource(R.string.alerts_car_crash)
                        TrafficJam -> stringResource(R.string.alerts_traffic_jam)
                        WildAnimals -> stringResource(R.string.alerts_wild_animals)
                        else -> stringResource(R.string.alerts_unsupported_message)
                    }

                    IncidentAlert(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing,
                            )
                        ),
                        emoji = alert.mapEventType.emoji,
                        title = title,
                        distance = alert.distance,
                        messages = alert.messages
                    )

                }

                is CameraAlert -> {
                    val title = when (alert.cameraType) {
                        StationaryCamera -> stringResource(R.string.alerts_stationary_camera)
                        MobileCamera -> stringResource(R.string.alerts_mobile_camera)
                        MediumSpeedCamera -> stringResource(R.string.alerts_medium_speed_camera)
                    }
                    val icon = when (alert.cameraType) {
                        StationaryCamera -> R.drawable.ic_stationary_camera
                        MobileCamera -> R.drawable.ic_mobile_camera
                        MediumSpeedCamera -> R.drawable.ic_medium_speed_camera
                    }
                    CameraAlert(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing,
                            )
                        ),
                        distance = alert.distance,
                        speedLimit = alert.speedLimit,
                        drawableId = icon,
                        title = title
                    )
                }
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun AlertsPreview() = GrodnoRoadsM3ThemePreview {
    Alerts(
        alerts = persistentListOf(
            IncidentAlert(
                mapEventType = TrafficPolice,
                distance = 1,
                id = Uuid.randomUUID(),
                messages = persistentListOf(
                    MessageItem(
                        message = "Славинского беларуснефть на скорость",
                        source = Source.Viber
                    )
                )
            ),
            CameraAlert(
                id = Uuid.randomUUID(),
                distance = 2,
                speedLimit = 60,
                cameraType = StationaryCamera
            ),
            IncidentAlert(
                id = Uuid.randomUUID(),
                distance = 5,
                messages = persistentListOf(
                    MessageItem(
                        message = "(15:30) Старый мост ДТП в правой полосе по направлению от кольца в центр",
                        source = Source.Viber
                    ),
                    MessageItem(
                        message = "(15:45) Новый мост в левой полосе по направлению",
                        source = Source.Viber
                    )
                ),
                mapEventType = RoadIncident
            ),
            CameraAlert(
                id = Uuid.randomUUID(),
                distance = 220,
                speedLimit = -1,
                cameraType = MobileCamera
            ),
            CameraAlert(
                id = Uuid.randomUUID(),
                distance = 220,
                speedLimit = 60,
                cameraType = MobileCamera
            )
        )
    )
}