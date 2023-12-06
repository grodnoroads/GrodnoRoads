package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoFilterChip() {
    UIDemoContainer(name = "FilterChip") {
        Column {
            Row {
                var isSelected by rememberMutableState { true }

                FilterChip(
                    selected = isSelected,
                    onClick = { isSelected = !isSelected },
                    label = {
                        Text("Filter Chip")
                    }
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = true,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
                FilterChip(
                    selected = true,
                    enabled = false,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
                FilterChip(
                    selected = false,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
                FilterChip(
                    selected = false,
                    enabled = false,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoFilterChipPreview() = GrodnoRoadsM3ThemePreview {
    DemoFilterChip()
}
