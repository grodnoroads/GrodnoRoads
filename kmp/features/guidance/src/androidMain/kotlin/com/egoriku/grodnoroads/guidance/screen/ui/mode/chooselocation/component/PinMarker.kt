package com.egoriku.grodnoroads.guidance.screen.ui.mode.chooselocation.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.iconpack.GrodnoRoadsIcons
import com.egoriku.grodnoroads.foundation.common.ui.iconpack.icons.icPinMarker
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
fun PinMarker(
    modifier: Modifier = Modifier,
    duration: Int = 450,
    verticalOffset: Dp = 20.dp,
    shadowScaleMax: Float = 0.9f,
    shadowScaleMin: Float = 0.6f,
    animate: Boolean,
    onGloballyPositioned: (Offset) -> Unit
) {
    val startColor = LocalContentColor.current.copy(alpha = 0.85f)
    val endColor = LocalContentColor.current.copy(alpha = 0.4f)

    var markerOffset by rememberMutableState(animate) { if (animate) -verticalOffset else 0.dp }
    val markerTranslation by animateDpAsState(
        target = markerOffset,
        duration = duration,
        label = "markerTranslation"
    ) {
        if (!animate) return@animateDpAsState
        markerOffset = if (it == -verticalOffset) 0.dp else -verticalOffset
    }

    var scale by rememberMutableState(animate) { if (animate) shadowScaleMin else shadowScaleMax }
    val scaleTranslation by animateFloatAsState(
        target = scale,
        duration = duration,
        label = "scaleTranslation"
    ) {
        if (!animate) return@animateFloatAsState
        scale = if (it == shadowScaleMax) shadowScaleMin else shadowScaleMax
    }

    var color by rememberMutableState(animate) { if (animate) endColor else startColor }
    val colorTransition by animateColorAsState(
        target = color,
        duration = duration,
        label = "colorTransition"
    ) {
        if (!animate) return@animateColorAsState
        color = if (it == endColor) startColor else endColor
    }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .width(16.dp)
                .aspectRatio(2f)
                .align { size, space, _ ->
                    IntOffset(
                        x = ((space.width - size.width) * 0.5f).toInt(),
                        y = (space.height - size.height * 0.5f).toInt()
                    )
                }
                .onGloballyPositioned {
                    val position = Offset(
                        x = it.positionInWindow().x + it.size.width / 2f,
                        y = it.positionInWindow().y + it.size.height / 2f,
                    )
                    onGloballyPositioned(position)
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            scale(scaleX = scaleTranslation, scaleY = scaleTranslation) {
                drawOval(
                    topLeft = Offset(x = 0f, y = canvasHeight / 4f),
                    color = colorTransition,
                    size = Size(width = canvasWidth, height = canvasHeight / 2f),
                )
            }
        }
        Icon(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
                .graphicsLayer {
                    translationY = markerTranslation.value
                },
            imageVector = GrodnoRoadsIcons.icPinMarker,
            contentDescription = null,
        )
    }
}

@Composable
private fun animateDpAsState(
    target: Dp,
    duration: Int,
    label: String,
    finishedListener: ((Dp) -> Unit)? = null
) = animateDpAsState(
    targetValue = target,
    animationSpec = tween(duration, easing = LinearEasing),
    finishedListener = finishedListener,
    label = label
)

@Composable
private fun animateFloatAsState(
    target: Float,
    duration: Int,
    label: String,
    finishedListener: ((Float) -> Unit)? = null
) = animateFloatAsState(
    targetValue = target,
    animationSpec = tween(duration, easing = LinearEasing),
    finishedListener = finishedListener,
    label = label
)

@Composable
private fun animateColorAsState(
    target: Color,
    duration: Int,
    label: String,
    finishedListener: ((Color) -> Unit)? = null
) = animateColorAsState(
    targetValue = target,
    animationSpec = tween(duration, easing = LinearEasing),
    finishedListener = finishedListener,
    label = label
)

@Preview(device = "id:Nexus One")
@Preview(device = "id:pixel_3a")
@Preview(device = "id:pixel_3a_xl", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = "id:pixel_6_pro")
@Preview(device = "id:Nexus 7")
@Preview(device = "id:pixel_c")
@Composable
private fun PinMarkerPreview() = GrodnoRoadsM3ThemePreview {
    Box(Modifier.size(300.dp)) {
        var animate by remember { mutableStateOf(false) }

        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = { animate = !animate },
        ) {
            Text("enabled: $animate")
        }

        PinMarker(
            modifier = Modifier.align(Alignment.Center),
            animate = animate,
            onGloballyPositioned = {}
        )
    }
}
