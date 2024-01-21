package com.egoriku.grodnoroads.settings.appearance.screen.bottomsheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.common.ConfirmationFooter
import com.egoriku.grodnoroads.foundation.common.ui.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.foundation.common.ui.lazycolumn.SingleChoiceLazyColumn
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLanguageBottomSheet(
    languageDialogState: LanguageDialogState,
    onCancel: () -> Unit,
    onResult: (AppearancePref) -> Unit
) {
    var language by rememberMutableState { languageDialogState.languages }

    val sheetCloseBehaviour = rememberSheetCloseBehaviour(
        onCancel = onCancel,
        onResult = { onResult(language) }
    )

    BasicModalBottomSheet(
        sheetState = sheetCloseBehaviour.sheetState,
        onCancel = onCancel,
        content = {
            SingleChoiceLazyColumn(
                list = language.values.map {
                    stringResource(id = it.toStringResource())
                }.toImmutableList(),
                contentPadding = PaddingValues(bottom = 16.dp),
                initialSelection = language.values.indexOf(language.current),
                onSelected = { position ->
                    language = language.copy(current = language.values[position])
                }
            )
            HorizontalDivider()
        },
        footer = {
            ConfirmationFooter(
                onDismiss = sheetCloseBehaviour::cancel,
                onAccept = sheetCloseBehaviour::hideWithResult
            )
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewAppLanguageBottomSheet() = GrodnoRoadsM3ThemePreview {
    AppLanguageBottomSheet(
        languageDialogState = LanguageDialogState(languages = AppearancePref.AppLanguage()),
        onCancel = { },
        onResult = {}
    )
}