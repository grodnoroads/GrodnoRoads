package com.egoriku.grodnoroads.guidance.screen.ui.mode.chooselocation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.guidance.screen.ui.mode.chooselocation.component.PinMarker

@Composable
fun ChooseLocation(
    isCameraMoving: Boolean,
    onCancel: () -> Unit,
    onLocationSelected: (Offset) -> Unit
) {
    var markerOffset = remember { Offset.Zero }

    Box(modifier = Modifier.fillMaxSize()) {
        PinMarker(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 60.dp),
            animate = isCameraMoving,
            onGloballyPositioned = {
                markerOffset = it
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally)
        ) {
            PrimaryInverseCircleButton(size = Large, onClick = onCancel) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
            PrimaryCircleButton(
                size = Large,
                onClick = { onLocationSelected(markerOffset) },
                enabled = !isCameraMoving,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun ChooseLocationPreview() = GrodnoRoadsM3ThemePreview {
    ChooseLocation(
        isCameraMoving = false,
        onCancel = {},
        onLocationSelected = {}
    )
}