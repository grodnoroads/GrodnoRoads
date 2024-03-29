package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.listitem.*
import com.egoriku.grodnoroads.uidemo.R
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoListItem() {
    UIDemoContainer(name = "ListItem") {
        var triState by rememberMutableState { ToggleableState.Off }
        TriStateCheckBoxListItem(
            text = "За рулем | Гродно",
            state = triState,
            onToggle = {
                triState = when (triState) {
                    ToggleableState.Indeterminate -> ToggleableState.Off
                    ToggleableState.Off -> ToggleableState.On
                    ToggleableState.On -> ToggleableState.Indeterminate
                }
            }
        )
        var state by rememberMutableState { true }
        CheckBoxListItem(
            paddingValues = PaddingValues(start = 34.dp),
            text = "ГАИ",
            isChecked = state,
            iconRes = R.drawable.ic_settings_traffic_police,
            iconSize = DpSize(32.dp, 32.dp),
            onCheckedChange = { state = it }
        )
        MoreActionListItem(
            imageVector = Icons.Default.LocationCity,
            text = "My city",
            value = "Grodno",
            onClick = {}
        )
        SimpleListItem(
            imageVector = Icons.Default.PlayCircle,
            text = "Test test test",
            onClick = {}
        )

        var isChecked1 by rememberMutableState { false }
        var isChecked2 by rememberMutableState { false }
        var isChecked3 by rememberMutableState { false }

        Column {
            SwitchListItem(
                imageVector = Icons.Default.Brightness7,
                text = "За рулем | Гродно",
                isChecked = isChecked1,
                onCheckedChange = { isChecked1 = it }
            )
            SwitchListItem(
                imageVector = Icons.Default.Brightness7,
                text = "За рулем | Гродно",
                description = "За рулем | Гродно",
                isChecked = isChecked2,
                onCheckedChange = { isChecked2 = it }
            )
            SwitchListItem(
                text = "За рулем | Гродно",
                description = "За рулем | Гродно",
                isChecked = isChecked3,
                onCheckedChange = { isChecked3 = it }
            )
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoListItemPreview() = GrodnoRoadsM3ThemePreview {
    DemoListItem()
}
