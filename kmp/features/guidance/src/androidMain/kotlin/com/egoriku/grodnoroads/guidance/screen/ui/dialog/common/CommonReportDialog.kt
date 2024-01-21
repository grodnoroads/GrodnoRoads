package com.egoriku.grodnoroads.guidance.screen.ui.dialog.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.egoriku.grodnoroads.foundation.common.ui.dialog.DialogContent
import com.egoriku.grodnoroads.foundation.common.ui.dialog.DialogTitle
import com.egoriku.grodnoroads.foundation.common.ui.dialog.ListItems
import com.egoriku.grodnoroads.foundation.common.ui.dialog.content.DialogButton
import com.egoriku.grodnoroads.foundation.common.ui.dialog.content.RadioButtonItem
import com.egoriku.grodnoroads.resources.R
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CommonReportDialog(
    titleRes: Int,
    actions: ImmutableList<String>,
    onClose: () -> Unit,
    onSelected: (selected: Int, inputText: String) -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(-1) }
    var inputText by remember { mutableStateOf("") }

    val sendButtonEnable by remember(selectedItem) {
        mutableStateOf(selectedItem != -1)
    }

    Dialog(onDismissRequest = onClose) {
        DialogContent {
            DialogTitle(
                titleRes = titleRes,
                center = true
            )

            ListItems(
                modifier = Modifier.weight(weight = 1f, fill = false),
                list = actions,
                onClick = { index, _ -> selectedItem = index },
                footer = {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        value = inputText,
                        singleLine = false,
                        onValueChange = { inputText = it },
                        label = { Text(stringResource(R.string.dialog_input_hint)) }
                    )
                }
            ) { index, item ->
                val selected = remember(selectedItem) { index == selectedItem }

                RadioButtonItem(
                    item = item,
                    index = index,
                    selected = selected,
                    onSelect = {
                        selectedItem = index
                    }
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                DialogButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.cancel,
                    onClick = onClose
                )
                DialogButton(
                    enabled = sendButtonEnable,
                    modifier = Modifier.weight(1f),
                    textResId = R.string.send,
                    onClick = {
                        onSelected(selectedItem, inputText)
                    }
                )
            }
        }
    }
}