package com.egoriku.grodnoroads.settings.map.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.*
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponentPreview
import com.egoriku.grodnoroads.settings.map.screen.ui.DefaultLocationSection
import com.egoriku.grodnoroads.settings.map.screen.ui.DrivingModeSection
import com.egoriku.grodnoroads.settings.map.screen.ui.MapEventsSection
import com.egoriku.grodnoroads.settings.map.screen.ui.MapStyleSection
import com.egoriku.grodnoroads.settings.map.screen.ui.bottomsheet.DefaultLocationBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSettingsScreen(
    mapSettingsComponent: MapSettingsComponent,
    onBack: () -> Unit
) {
    val state by mapSettingsComponent.state.collectAsState(initial = MapSettingState())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_map),
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DialogHandler(
                dialogState = state.mapDialogState,
                onClose = mapSettingsComponent::closeDialog,
                onResult = mapSettingsComponent::modify
            )

            if (!state.isLoading) {
                LoadedState(
                    mapSettingState = state,
                    openDialog = mapSettingsComponent::openDialog,
                    modify = mapSettingsComponent::modify,
                    reset = mapSettingsComponent::reset
                )
            }
        }
    }
}

@Composable
private fun LoadedState(
    mapSettingState: MapSettingState,
    openDialog: (MapPref) -> Unit,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        DefaultLocationSection(
            locationInfo = mapSettingState.mapSettings.locationInfo,
            onCheckedChange = openDialog
        )
        DrivingModeSection(
            driveModeZoom = mapSettingState.mapSettings.driveModeZoom,
            modify = modify,
            reset = reset
        )
        MapStyleSection(
            mapStyle = mapSettingState.mapSettings.mapStyle,
            onCheckedChange = modify
        )
        MapEventsSection(
            mapInfo = mapSettingState.mapSettings.mapInfo,
            onCheckedChange = modify
        )
    }
}

@Composable
private fun DialogHandler(
    dialogState: MapDialogState,
    onClose: () -> Unit,
    onResult: (MapPref) -> Unit
) {
    when (dialogState) {
        is DefaultLocationDialogState -> {
            DefaultLocationBottomSheet(
                defaultLocationState = dialogState,
                onCancel = onClose,
                onResult = onResult
            )
        }

        is None -> {}
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun MapSettingsScreenPreview() = GrodnoRoadsM3ThemePreview {
    MapSettingsScreen(
        mapSettingsComponent = MapSettingsComponentPreview(),
        onBack = {}
    )
}