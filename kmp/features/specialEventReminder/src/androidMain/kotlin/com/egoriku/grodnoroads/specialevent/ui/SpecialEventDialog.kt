package com.egoriku.grodnoroads.specialevent.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.foundation.common.ui.dialog.DialogContent
import com.egoriku.grodnoroads.foundation.common.ui.dialog.content.DialogButton
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Autumn
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Spring

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialEventDialog(eventType: EventType, onClose: () -> Unit) {
    val dialogContent = when (eventType) {
        Spring -> stringResource(id = R.string.headlamp_body_spring)
        Autumn -> stringResource(id = R.string.headlamp_body_autumn)
    }

    BasicAlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false
        ),
        onDismissRequest = onClose
    ) {
        DialogContent {
            Column(verticalArrangement = Arrangement.Center) {
                Text(modifier = Modifier.padding(16.dp), text = dialogContent)
                HorizontalDivider()
                DialogButton(
                    modifier = Modifier.fillMaxWidth(),
                    textResId = R.string.ok,
                    onClick = onClose
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSpecialEventDialogSpring() = GrodnoRoadsM3ThemePreview {
    SpecialEventDialog(eventType = Spring) {}
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSpecialEventDialogAutumn() = GrodnoRoadsM3ThemePreview {
    SpecialEventDialog(eventType = Autumn) {}
}