package com.egoriku.grodnoroads.settings.alerts.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.list.CheckboxSettings
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.checkbox.TriStateCheckbox
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.AlertEvents
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.*
import com.egoriku.grodnoroads.shared.persistent.Selectable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertEventsSection(
    alertEvents: AlertEvents,
    onCheckedChange: (AlertsPref) -> Unit
) {
    Column {
        val state by rememberMutableState(alertEvents.selectable) {
            when (alertEvents.selectable) {
                Selectable.AllDisabled -> ToggleableState.Off
                Selectable.AllEnabled -> ToggleableState.On
                Selectable.Mixed -> ToggleableState.Indeterminate
            }
        }
        val onToggle = remember(alertEvents.selectable) {
            {
                val selectAll = alertEvents.selectable != Selectable.AllEnabled
                onCheckedChange(SelectAll(selectAll = selectAll))
            }
        }

        SettingsHeader(title = stringResource(id = R.string.alerts_header_voice_alerts))
        ListItem(
            modifier = Modifier
                .height(48.dp)
                .triStateToggleable(state = state, onClick = onToggle),
            leadingContent = {
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    TriStateCheckbox(
                        modifier = Modifier.padding(start = 6.dp),
                        state = state,
                        onClick = onToggle
                    )
                }
            },
            headlineContent = {
                Text(text = stringResource(id = R.string.alerts_notifications_select_all))
            },
        )
        StationaryCameras(
            stationaryCameras = alertEvents.stationaryCameras,
            onCheckedChange = onCheckedChange
        )
        MediumSpeedCameras(
            mediumSpeedCameras = alertEvents.mediumSpeedCameras,
            onCheckedChange = onCheckedChange
        )
        MobileCameras(
            mobileCameras = alertEvents.mobileCameras,
            onCheckedChange = onCheckedChange
        )
        TrafficPolice(
            trafficPolice = alertEvents.trafficPolice,
            onCheckedChange = onCheckedChange
        )
        WildAnimals(
            wildAnimals = alertEvents.wildAnimals,
            onCheckedChange = onCheckedChange
        )
        RoadIncidents(
            roadIncident = alertEvents.roadIncident,
            onCheckedChange = onCheckedChange
        )
        TrafficConditions(
            trafficJam = alertEvents.trafficJam,
            onCheckedChange = onCheckedChange
        )
        CarCrash(
            carCrash = alertEvents.carCrash,
            onCheckedChange = onCheckedChange
        )
    }
}

private val subGroupPadding = 44.dp

@Composable
private fun StationaryCameras(
    stationaryCameras: StationaryCameras,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_stationary_camera,
        stringResId = R.string.alerts_notifications_stationary_cameras,
        isChecked = stationaryCameras.isNotify,
        onCheckedChange = {
            onCheckedChange(stationaryCameras.copy(isNotify = it))
        }
    )
}

@Composable
private fun MediumSpeedCameras(
    mediumSpeedCameras: MediumSpeedCameras,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_medium_speed_camera,
        stringResId = R.string.alerts_notifications_medium_speed_cameras,
        isChecked = mediumSpeedCameras.isNotify,
        onCheckedChange = {
            onCheckedChange(mediumSpeedCameras.copy(isNotify = it))
        }
    )
}

@Composable
private fun MobileCameras(
    mobileCameras: MobileCameras,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_mobile_camera,
        stringResId = R.string.alerts_notifications_mobile_cameras,
        isChecked = mobileCameras.isNotify,
        onCheckedChange = {
            onCheckedChange(mobileCameras.copy(isNotify = it))
        }
    )
}

@Composable
private fun TrafficPolice(
    trafficPolice: TrafficPolice,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_settings_traffic_police,
        stringResId = R.string.alerts_notifications_traffic_police,
        isChecked = trafficPolice.isNotify,
        onCheckedChange = {
            onCheckedChange(trafficPolice.copy(isNotify = it))
        }
    )
}

@Composable
private fun RoadIncidents(
    roadIncident: RoadIncident,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_settings_road_incident,
        stringResId = R.string.alerts_notifications_incidents,
        isChecked = roadIncident.isNotify,
        onCheckedChange = {
            onCheckedChange(roadIncident.copy(isNotify = it))
        }
    )
}

@Composable
private fun CarCrash(
    carCrash: CarCrash,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_settings_car_crash,
        stringResId = R.string.alerts_notifications_car_crash,
        isChecked = carCrash.isNotify,
        onCheckedChange = {
            onCheckedChange(carCrash.copy(isNotify = it))
        }
    )
}

@Composable
private fun TrafficConditions(
    trafficJam: TrafficJam,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_settings_traffic_jam,
        stringResId = R.string.alerts_notifications_traffic_jam,
        isChecked = trafficJam.isNotify,
        onCheckedChange = {
            onCheckedChange(trafficJam.copy(isNotify = it))
        }
    )
}

@Composable
private fun WildAnimals(
    wildAnimals: WildAnimals,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = subGroupPadding),
        iconRes = R.drawable.ic_settings_wild_animals,
        stringResId = R.string.alerts_notifications_wild_animals,
        isChecked = wildAnimals.isNotify,
        onCheckedChange = {
            onCheckedChange(wildAnimals.copy(isNotify = it))
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun AlertEventsSectionPreview() = GrodnoRoadsM3ThemePreview {
    AlertEventsSection(alertEvents = AlertEvents()) {}
}