package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.list.MoreActionSettings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.shared.persistent.toStringResource

@Composable
internal fun DefaultLocationSection(
    locationInfo: LocationInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(title = stringResource(id = R.string.map_header_default_location))

        val defaultCity = locationInfo.defaultCity
        MoreActionSettings(
            iconResId = R.drawable.ic_my_city,
            text = stringResource(id = R.string.map_default_location),
            value = stringResource(id = defaultCity.current.toStringResource())
        ) {
            onCheckedChange(defaultCity)
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewDefaultLocationSection() = GrodnoRoadsM3ThemePreview {
    DefaultLocationSection(locationInfo = LocationInfo()) {}
}