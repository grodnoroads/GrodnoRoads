package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.list.ActionSettings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.ClickableFloatRange
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.DriveModeZoom

@Composable
internal fun DrivingModeSection(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(title = stringResource(id = R.string.map_header_drive_mode))

        MapZoomInCity(driveModeZoom = driveModeZoom, modify = modify, reset = reset)
        MapZoomOutCity(driveModeZoom = driveModeZoom, modify = modify, reset = reset)
    }
}

@Composable
private fun MapZoomInCity(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    val mapZoomInCity = driveModeZoom.mapZoomInCity

    ActionSettings(
        iconResId = R.drawable.ic_inside_city,
        text = stringResource(R.string.map_header_drive_mode_zoom_in_city),
        trailing = {
            ClickableFloatRange(
                value = mapZoomInCity.current,
                min = mapZoomInCity.min,
                max = mapZoomInCity.max,
                step = mapZoomInCity.stepSize,
                onLongClick = { reset(driveModeZoom.mapZoomInCity) },
                onValueChange = {
                    modify(mapZoomInCity.copy(current = it))
                },
            )
        }
    )
}

@Composable
private fun MapZoomOutCity(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    val mapZoomOutCity = driveModeZoom.mapZoomOutCity

    ActionSettings(
        iconResId = R.drawable.ic_outside_city,
        text = stringResource(R.string.map_header_drive_mode_zoom_outside_city),
        trailing = {
            ClickableFloatRange(
                value = mapZoomOutCity.current,
                min = mapZoomOutCity.min,
                max = mapZoomOutCity.max,
                step = mapZoomOutCity.stepSize,
                onLongClick = { reset(mapZoomOutCity) },
                onValueChange = {
                    modify(mapZoomOutCity.copy(current = it))
                },
            )
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun DrivingModeSectionSectionPreview() = GrodnoRoadsM3ThemePreview {
    DrivingModeSection(
        driveModeZoom = DriveModeZoom(),
        modify = {},
        reset = {}
    )
}