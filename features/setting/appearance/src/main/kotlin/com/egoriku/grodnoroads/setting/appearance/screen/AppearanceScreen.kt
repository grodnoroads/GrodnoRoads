package com.egoriku.grodnoroads.setting.appearance.screen

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
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.listitem.MoreActionListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.SwitchListItem
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent.AppearanceDialogState
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent.AppearancePref.*
import com.egoriku.grodnoroads.setting.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.setting.appearance.screen.bottomsheet.AppLanguageBottomSheet
import com.egoriku.grodnoroads.setting.appearance.screen.bottomsheet.AppThemeBottomSheet
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language.Companion.toStringResource
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.Companion.toStringResource

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
            VerticalSpacer(16.dp)
            SettingsSectionHeader(title = stringResource(id = R.string.settings_category_other))
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

    MoreActionListItem(
        imageVector = Icons.Default.Language,
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

    MoreActionListItem(
        imageVector = Icons.Default.DarkMode,
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

    SwitchListItem(
        imageVector = Icons.Default.Brightness7,
        text = stringResource(R.string.appearance_keep_screen_on),
        description = stringResource(R.string.appearance_keep_screen_on_description),
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