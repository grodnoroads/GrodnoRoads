package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.domain.model.Source

@Composable
fun MessageRow(messageItem: MessageItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SourceImage(
            modifier = Modifier
                .padding(end = 6.dp)
                .align(Alignment.Top),
            source = messageItem.source
        )
        Text(
            text = messageItem.message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewMessageRow() = GrodnoRoadsM3ThemePreview {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MessageRow(
            messageItem = MessageItem(
                message = "Test message\nTest message",
                source = Source.App
            )
        )
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.Viber))
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.Telegram))
    }
}