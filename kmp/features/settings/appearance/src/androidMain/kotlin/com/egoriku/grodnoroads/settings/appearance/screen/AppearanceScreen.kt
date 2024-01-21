package com.egoriku.grodnoroads.settings.appearance.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.common.ui.list.MoreActionSettings
import com.egoriku.grodnoroads.foundation.common.ui.list.SwitchSettings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.*
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponentPreview
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.settings.appearance.screen.bottomsheet.AppLanguageBottomSheet
import com.egoriku.grodnoroads.settings.appearance.screen.bottomsheet.AppThemeBottomSheet
import com.egoriku.grodnoroads.shared.persistent.toStringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    appearanceComponent: AppearanceComponent,
    onBack: () -> Unit
) {
    val state by appearanceComponent.state.collectAsState(initial = State())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    DialogHandler(
        dialogState = state.dialogState,
        onClose = appearanceComponent::closeDialog,
        onResult = appearanceComponent::update
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_appearance),
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            AppThemeSection(state = state, onModify = appearanceComponent::modify)
            LanguageSection(state = state, onModify = appearanceComponent::modify)

            SettingsHeader(title = stringResource(id = R.string.settings_category_other))
            KeepScreenOnSettings(state = state, onModify = appearanceComponent::update)
        }
    }
}

@Composable
private fun LanguageSection(
    state: State,
    onModify: (AppLanguage) -> Unit
) {
    val language = state.appearanceState.appLanguage

    MoreActionSettings(
        icon = Icons.Default.Language,
        text = stringResource(R.string.appearance_app_language),
        value = stringResource(id = language.current.toStringResource()),
        onClick = { onModify(language) },
    )
}

@Composable
private fun AppThemeSection(
    state: State,
    onModify: (AppTheme) -> Unit,
) {
    val appTheme = state.appearanceState.appTheme

    MoreActionSettings(
        icon = Icons.Default.DarkMode,
        text = stringResource(R.string.appearance_app_theme),
        value = stringResource(id = appTheme.current.toStringResource()),
        onClick = { onModify(appTheme) },
    )
}

@Composable
private fun KeepScreenOnSettings(
    state: State,
    onModify: (KeepScreenOn) -> Unit
) {
    val keepScreenOn = state.appearanceState.keepScreenOn

    SwitchSettings(
        imageVector = Icons.Default.Brightness7,
        stringResId = R.string.appearance_keep_screen_on,
        supportingResId = R.string.appearance_keep_screen_on_description,
        isChecked = keepScreenOn.enabled,
        onCheckedChange = {
            onModify(keepScreenOn.copy(enabled = it))
        }
    )
}

@Composable
private fun DialogHandler(
    dialogState: AppearanceDialogState,
    onClose: () -> Unit,
    onResult: (AppearancePref) -> Unit
) {
    when (dialogState) {
        is ThemeDialogState -> {
            AppThemeBottomSheet(
                themeDialogState = dialogState,
                onCancel = onClose,
                onResult = onResult
            )
        }

        is LanguageDialogState -> {
            AppLanguageBottomSheet(
                languageDialogState = dialogState,
                onCancel = onClose,
                onResult = {
                    onClose()
                    onResult(it)
                }
            )
        }

        else -> {}
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun AppearanceScreenPreview() = GrodnoRoadsM3ThemePreview {
    AppearanceScreen(
        appearanceComponent = AppearanceComponentPreview(),
        onBack = {}
    )
}