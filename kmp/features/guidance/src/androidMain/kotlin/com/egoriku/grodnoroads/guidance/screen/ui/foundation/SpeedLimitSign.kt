package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview

@Composable
fun SpeedLimitSign(
    limit: Int,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val borderSize = when (fontSize) {
        TextUnit.Unspecified -> 3.dp
        else -> 5.dp
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.White, shape = CircleShape)
            .border(borderSize, Color.Red, CircleShape)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)

                val currentHeight = placeable.height
                var heightCircle = currentHeight
                if (placeable.width > heightCircle)
                    heightCircle = placeable.width

                layout(heightCircle, heightCircle) {
                    placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                }
            }
    ) {
        Text(
            text = limit.toString(),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    when (fontSize) {
                        TextUnit.Unspecified -> 4.dp
                        else -> fontSize.value.dp / 2f
                    }
                )
                .defaultMinSize(
                    when (fontSize) {
                        TextUnit.Unspecified -> 24.dp
                        else -> fontSize.value.dp * 1.5f
                    }
                )
        )
    }
}

@GrodnoRoadsPreview
@Composable
fun SpeedLimitSignPreview() {
    Column {
        SpeedLimitSign(limit = 70)
        SpeedLimitSign(fontSize = 30.sp, limit = 70)
        SpeedLimitSign(fontSize = 30.sp, limit = 100)
    }
}