package com.egoriku.grodnoroads.screen.settings.map.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.list.MoreActionSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Companion.toResource
import com.egoriku.grodnoroads.screen.settings.ui.SettingsHeader
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun DefaultLocationSection(
    defaultCity: DefaultCity,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(
            title = stringResource(id = R.string.map_header_default_location),
            top = 16.dp
        )
        MoreActionSettings(
            icon = Icons.Default.LocationCity,
            text = stringResource(id = R.string.map_default_location),
            value = stringResource(id = defaultCity.current.toResource())
        ) {
            onCheckedChange(defaultCity)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Preview(showBackground = true, locale = "be")
@Composable
private fun PreviewDefaultLocationSection() {
    GrodnoRoadsTheme {
        DefaultLocationSection(defaultCity = DefaultCity()) {}
    }
}