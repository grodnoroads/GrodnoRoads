package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.list.SwitchSettings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.MapStyle
import com.egoriku.grodnoroads.settings.map.screen.ui.component.CheckableCard
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.Style

@Composable
internal fun MapStyleSection(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(title = stringResource(id = R.string.map_header_appearance))

        GoogleMapStyle(mapStyle, onCheckedChange)
        TrafficConditions(mapStyle, onCheckedChange)
    }
}

@Composable
private fun GoogleMapStyle(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    val googleMapStyle = mapStyle.googleMapStyle

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        CheckableCard(
            title = R.string.map_google_map_style_minimal,
            selected = googleMapStyle.style == Style.Minimal,
            iconId = if (MaterialTheme.colorScheme.isLight) {
                R.drawable.ic_map_style_minimal_light
            } else {
                R.drawable.ic_map_style_minimal_night
            },
            onClick = {
                onCheckedChange(googleMapStyle.copy(style = Style.Minimal))
            }
        )

        CheckableCard(
            title = R.string.map_google_map_style_detailed,
            selected = googleMapStyle.style == Style.Detailed,
            iconId = if (MaterialTheme.colorScheme.isLight) {
                R.drawable.ic_map_style_detailed_light
            } else {
                R.drawable.ic_map_style_detailed_night
            },
            onClick = {
                onCheckedChange(googleMapStyle.copy(style = Style.Detailed))
            }
        )
    }
}

@Composable
private fun TrafficConditions(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    val trafficJamOnMap = mapStyle.trafficJamOnMap

    SwitchSettings(
        modifier = Modifier.padding(start = 8.dp),
        stringResId = R.string.map_traffic_conditions_appearance,
        isChecked = trafficJamOnMap.isShow,
        onCheckedChange = {
            onCheckedChange(trafficJamOnMap.copy(isShow = it))
        }
    )
}

@GrodnoRoadsPreview
@Composable
fun PreviewMapStyleSection() = GrodnoRoadsM3ThemePreview {
    MapStyleSection(mapStyle = MapStyle()) { }
}
