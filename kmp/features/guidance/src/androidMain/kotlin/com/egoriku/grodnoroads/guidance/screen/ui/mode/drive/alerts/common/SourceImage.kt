package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.guidance.domain.model.Source
import com.egoriku.grodnoroads.resources.R

@Composable
fun SourceImage(modifier: Modifier = Modifier, source: Source) {
    Image(
        modifier = modifier.size(24.dp),
        painter = when (source) {
            Source.Viber -> painterResource(R.drawable.ic_viber)
            Source.Telegram -> painterResource(R.drawable.ic_telegram_logo)
            Source.App -> painterResource(R.drawable.ic_app_logo)
            // TODO: Add Zello icon
            Source.Zello -> painterResource(R.drawable.ic_app_logo)
        },
        contentDescription = "Source App"
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSourceImage() = GrodnoRoadsM3ThemePreview {
    Column {
        SourceImage(source = Source.App)
        SourceImage(source = Source.Viber)
        SourceImage(source = Source.Telegram)
    }
}