package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButtonGroup
import com.egoriku.grodnoroads.foundation.uikit.button.ActionIcon
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoActionButtonGroup() {
    UIDemoContainer(name = "ActionButtonGroup") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButtonGroup {
                ActionIcon(imageVector = Icons.Default.Add, onClick = {})
                ActionIcon(imageVector = Icons.Default.GpsFixed, onClick = {})
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoPrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoActionButtonGroup()
}