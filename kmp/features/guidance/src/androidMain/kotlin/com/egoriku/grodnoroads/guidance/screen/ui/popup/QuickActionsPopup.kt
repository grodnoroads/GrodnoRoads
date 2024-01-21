package com.egoriku.grodnoroads.guidance.screen.ui.popup

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.noIndicationClick
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Small
import com.egoriku.grodnoroads.resources.R

@Composable
fun QuickActionsPopup(
    opened: Boolean,
    onExpand: () -> Unit,
    onClosed: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(targetState = opened, label = "QuickActions")

    val percent by transition.animateDp(
        label = "animateClipShape",
        transitionSpec = {
            spring(stiffness = if (initialState) Spring.StiffnessVeryLow else Spring.StiffnessMedium)
        }
    ) { isOpen -> if (isOpen) 25.dp else 50.dp }

    BackHandler(enabled = opened, onBack = onClosed)

    Box(modifier = Modifier.fillMaxSize()) {
        FadeInOutAnimatedVisibility(
            visible = opened,
            modifier = Modifier.matchParentSize(),
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f))
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { onClosed() })
                    }
            )
        }
        Box(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                    .padding(1.dp)
                    .clip(shape = RoundedCornerShape(percent))
                    .noIndicationClick()
                    .background(MaterialTheme.colorScheme.surface)
                    .animateContentSize(animationSpec = spring(Spring.DampingRatioLowBouncy))
            ) {
                AnimatedVisibility(
                    visible = opened,
                    enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)),
                    exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
                    modifier = Modifier.animateContentSize(
                        animationSpec = spring(Spring.DampingRatioLowBouncy)
                    )
                ) {
                    AnimatedVisibility(
                        visible = opened,
                        enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)),
                        exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
                    ) {
                        Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.map_quick_actions_header),
                                    style = MaterialTheme.typography.titleLarge
                                )
                                CloseButton(onClick = onClosed)
                            }
                            Box(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 8.dp,
                                        bottom = 32.dp
                                    )
                            ) {
                                content()
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = !opened,
                enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessVeryLow)),
                exit = fadeOut(),
            ) {
                PrimaryInverseCircleButton(onClick = onExpand, size = Small) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    Box(
        Modifier
            .padding(vertical = 8.dp)
            .size(40.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun QuickActionsPopupPreview() = GrodnoRoadsM3ThemePreview {
    var opened by rememberMutableState { true }

    QuickActionsPopup(
        opened = opened,
        onExpand = { opened = true },
        onClosed = { opened = false },
    ) {
        Row {
            Text("Text Text Text Text Text Text Text Text")
        }
    }
}
