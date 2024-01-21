package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.egoriku.grodnoroads.foundation.theme.tonalElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ModalBottomSheet(
    data: T?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = MaterialTheme.tonalElevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    content: @Composable ColumnScope.(T) -> Unit,
) {
    if (data != null) {
        val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            dragHandle = dragHandle,
            windowInsets = windowInsets
        ) {
            Column(modifier = Modifier.padding(bottom = bottomPadding)) {
                content(data)
            }
        }
    }
}