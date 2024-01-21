package com.egoriku.grodnoroads.settings.map.screen.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun CheckableCard(
    title: Int,
    selected: Boolean,
    iconId: Int,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.width(120.dp)) {
        Card(
            modifier = Modifier.size(120.dp),
            onClick = onClick,
            shape = RoundedCornerShape(10.dp),
            border = when {
                selected -> BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                else -> null
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = stringResource(id = title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun CheckableCardPreview() = GrodnoRoadsM3ThemePreview {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        CheckableCard(
            title = R.string.map_google_map_style_minimal,
            selected = false,
            iconId = R.drawable.ic_map_style_minimal_night
        ) {}
        CheckableCard(
            title = R.string.map_google_map_style_detailed,
            selected = true,
            iconId = R.drawable.ic_map_style_detailed_night
        ) {}
    }
}