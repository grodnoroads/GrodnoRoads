package com.egoriku.grodnoroads.setting.alerts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.listitem.CheckBoxListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.TriStateCheckBoxListItem
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertSettings.AlertEvents
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertsPref.*
import com.egoriku.grodnoroads.shared.appsettings.types.Selectable

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

        SettingsSectionHeader(title = stringResource(id = R.string.alerts_header_voice_alerts))

        TriStateCheckBoxListItem(
            text = stringResource(id = R.string.alerts_notifications_select_all), state = state,
            onToggle = onToggle
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            StationaryCameras(alertEvents.stationaryCameras, onCheckedChange)
            MediumSpeedCameras(alertEvents.mediumSpeedCameras, onCheckedChange)
            MobileCameras(alertEvents.mobileCameras, onCheckedChange)
            TrafficPolice(alertEvents.trafficPolice, onCheckedChange)
            WildAnimals(alertEvents.wildAnimals, onCheckedChange)
            RoadIncidents(alertEvents.roadIncident, onCheckedChange)
            TrafficConditions(alertEvents.trafficJam, onCheckedChange)
            CarCrash(alertEvents.carCrash, onCheckedChange)
        }
    }
}

private val subGroupPaddingValues = PaddingValues(start = 34.dp)
private val iconSize = DpSize(32.dp, 32.dp)

@Composable
private fun StationaryCameras(
    stationaryCameras: StationaryCameras,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_stationary_camera,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_stationary_cameras),
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
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_medium_speed_camera,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_medium_speed_cameras),
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
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_mobile_camera,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_mobile_cameras),
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
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_settings_traffic_police,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_traffic_police),
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
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_settings_road_incident,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_incidents),
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
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_settings_car_crash,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_car_crash),
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
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_settings_traffic_jam,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_traffic_jam),
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
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        iconRes = R.drawable.ic_settings_wild_animals,
        iconSize = iconSize,
        text = stringResource(R.string.alerts_notifications_wild_animals),
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