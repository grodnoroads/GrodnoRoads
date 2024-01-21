package com.egoriku.grodnoroads.settings.alerts.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.list.ActionSettings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.ClickableIntRange
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.AlertRadius
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref

@Composable
fun AlertRadiusSection(
    alertRadius: AlertRadius,
    modify: (AlertsPref) -> Unit,
    reset: (AlertsPref) -> Unit
) {
    Column {
        SettingsHeader(
            title = stringResource(id = R.string.alerts_header_notification_radius),
            paddingValues = PaddingValues(start = 24.dp, bottom = 4.dp)
        )

        val postfix = stringResource(id = R.string.alerts_notification_radius_postfix)
        val radiusInCity = alertRadius.alertRadiusInCity
        ActionSettings(
            iconResId = R.drawable.ic_inside_city,
            text = stringResource(R.string.alerts_notification_radius_in_city),
            trailing = {
                ClickableIntRange(
                    value = radiusInCity.current,
                    min = radiusInCity.min,
                    max = radiusInCity.max,
                    step = radiusInCity.stepSize,
                    formatter = { "$it $postfix" },
                    onLongClick = { reset(alertRadius.alertRadiusInCity) },
                    onValueChange = {
                        modify(radiusInCity.copy(current = it))
                    },
                )
            }
        )

        val radiusOutCity = alertRadius.alertRadiusOutCity
        ActionSettings(
            iconResId = R.drawable.ic_outside_city,
            text = stringResource(R.string.alerts_notification_radius_outside_city),
            trailing = {
                ClickableIntRange(
                    value = radiusOutCity.current,
                    min = radiusOutCity.min,
                    max = radiusOutCity.max,
                    step = radiusOutCity.stepSize,
                    formatter = { "$it $postfix" },
                    onLongClick = { reset(radiusOutCity) },
                    onValueChange = {
                        modify(radiusOutCity.copy(current = it))
                    },
                )
            }
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun AlertRadiusSectionPreview() = GrodnoRoadsM3ThemePreview {
    AlertRadiusSection(alertRadius = AlertRadius(), modify = {}, reset = {})
}