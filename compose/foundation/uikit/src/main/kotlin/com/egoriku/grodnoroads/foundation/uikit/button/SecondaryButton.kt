package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun SecondaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(16.dp)) {
        SecondaryButton(onClick = {}) {
            Text(text = "Sample text")
        }
    }
}