package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.guidance.screen.ui.KeepScreenOn
import com.egoriku.grodnoroads.resources.R

@Composable
fun DriveMode(
    modifier: Modifier = Modifier,
    stopDrive: () -> Unit,
    reportPolice: () -> Unit,
    reportIncident: () -> Unit
) {
    KeepScreenOn()
    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            PrimaryInverseCircleButton(onClick = reportPolice, size = Large) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.ic_traffic_police),
                    contentDescription = ""
                )
            }
            PrimaryInverseCircleButton(onClick = reportIncident, size = Large) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = ""
                )
            }
        }
        ActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            imageVector = Icons.Default.Close,
            onClick = stopDrive
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun DriveModePReview() = GrodnoRoadsM3ThemePreview {
    DriveMode(
        modifier = Modifier.padding(top = 24.dp),
        stopDrive = {},
        reportPolice = {},
        reportIncident = {}
    )
}