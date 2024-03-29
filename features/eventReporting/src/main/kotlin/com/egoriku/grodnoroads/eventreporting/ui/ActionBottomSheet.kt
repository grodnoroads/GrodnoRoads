package com.egoriku.grodnoroads.eventreporting.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.eventreporting.ui.util.preUpPostDownNestedScrollConnection
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryButton
import com.egoriku.grodnoroads.resources.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

internal enum class DragAnchors {
    Start,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ActionBottomSheet(
    onDismiss: () -> Unit,
    onResult: () -> Unit,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.End,
            animationSpec = tween(),
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 50.dp.toPx() } },
        )
    }
    val internalOnDismissRequest: () -> Unit = remember {
        {
            scope.launch {
                anchoredDraggableState.animateTo(DragAnchors.End)
            }.invokeOnCompletion {
                onDismiss()
            }
        }
    }

    BackHandler(onBack = internalOnDismissRequest)

    var height by rememberMutableState { 0 }

    val diff by remember {
        derivedStateOf { height - anchoredDraggableState.offset }
    }
    val isExpanded by remember {
        derivedStateOf { diff > 80 || diff.isNaN() }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = true) {
            Scrim(
                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
                onDismissRequest = internalOnDismissRequest,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = anchoredDraggableState
                                .requireOffset()
                                .toInt()
                        )
                    }
                    .nestedScroll(
                        remember(anchoredDraggableState) {
                            anchoredDraggableState.preUpPostDownNestedScrollConnection()
                        },
                    )
                    .onSizeChanged {
                        height = it.height

                        val anchors = DraggableAnchors {
                            DragAnchors.Start at 0f
                            DragAnchors.End at it.height.toFloat()
                        }
                        anchoredDraggableState.updateAnchors(anchors)
                    }
                    .anchoredDraggable(
                        state = anchoredDraggableState,
                        orientation = Orientation.Vertical
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    ) {
                        val alpha by animateFloatAsState(
                            targetValue = if (isExpanded) 1f else 0f,
                            label = "alpha"
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            DragHandle()
                            Box(
                                modifier = Modifier.graphicsLayer {
                                    this.alpha = alpha
                                }
                            ) {
                                content()
                            }
                        }
                    }
                }

                LaunchedEffect(anchoredDraggableState.anchors.hasAnchorFor(DragAnchors.Start)) {
                    anchoredDraggableState.animateTo(DragAnchors.Start)
                }

                LaunchedEffect(anchoredDraggableState) {
                    snapshotFlow { anchoredDraggableState.currentValue }
                        .drop(1)
                        .filter { it == DragAnchors.End }
                        .collectLatest {
                            onDismiss()
                        }
                }
            }

            if (!diff.isNaN()) {
                var measuredHeight by rememberMutableState { 0 }

                val offsetY by animateIntAsState(
                    targetValue = if (isExpanded) -measuredHeight else 0,
                    label = "offset"
                )

                BottomActions(
                    modifier = Modifier
                        .onSizeChanged {
                            if (measuredHeight < it.height) {
                                measuredHeight = it.height
                            }
                        }
                        .offset {
                            IntOffset(x = 0, y = measuredHeight)
                        }
                        .offset {
                            IntOffset(x = 0, y = offsetY)
                        },
                    onCancel = internalOnDismissRequest,
                    onResult = {
                        onResult()
                        internalOnDismissRequest()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomActions(
    modifier: Modifier,
    onCancel: () -> Unit,
    onResult: () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        ) {
            SecondaryButton(
                modifier = Modifier.weight(1f),
                onClick = onCancel
            ) {
                Text(text = stringResource(R.string.cancel))
            }

            PrimaryButton(
                modifier = Modifier.weight(1f),
                onClick = onResult
            ) {
                Text(text = stringResource(R.string.send))
            }
        }
    }
}

@Composable
private fun DragHandle() {
    Surface(
        modifier = Modifier.padding(vertical = 16.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Spacer(modifier = Modifier.size(width = 32.dp, height = 4.dp))
    }
}

@Composable
private fun Scrim(
    color: Color,
    onDismissRequest: () -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(),
        label = "alpha"
    )
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(onDismissRequest) {
                detectTapGestures {
                    onDismissRequest()
                }
            }
            .clearAndSetSemantics {}
    ) {
        drawRect(color = color, alpha = alpha)
    }
}
