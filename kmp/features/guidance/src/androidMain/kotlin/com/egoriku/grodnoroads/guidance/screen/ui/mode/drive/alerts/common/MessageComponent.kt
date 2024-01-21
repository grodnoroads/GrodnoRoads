package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.domain.model.Source
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MessageComponent(
    modifier: Modifier = Modifier,
    messages: ImmutableList<MessageItem>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        messages.forEach {
            MessageRow(messageItem = it)
        }
    }
}

@GrodnoRoadsPreview
@Composable
fun PreviewMessageComponent() = GrodnoRoadsM3ThemePreview {
    MessageComponent(
        messages = persistentListOf(
            MessageItem(message = "Test message 1\nTest message 1", source = Source.App),
            MessageItem(message = "Test message 2", source = Source.Viber),
            MessageItem(message = "Test message 3", source = Source.Telegram)
        )
    )
}