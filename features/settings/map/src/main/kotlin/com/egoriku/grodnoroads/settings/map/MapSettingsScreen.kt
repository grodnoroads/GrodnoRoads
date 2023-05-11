package com.egoriku.grodnoroads.settings.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.VerticalSpacer
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.*
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.settings.map.ui.DefaultLocationSection
import com.egoriku.grodnoroads.settings.map.ui.DrivingModeSection
import com.egoriku.grodnoroads.settings.map.ui.MapEventsSection
import com.egoriku.grodnoroads.settings.map.ui.MapStyleSection
import com.egoriku.grodnoroads.settings.map.ui.dialog.DefaultLocationDialog

@Composable
fun MapSettingsScreen(
    mapSettingsComponent: MapSettingsComponent,
    onBack: () -> Unit
) {
    BottomBarVisibility(HIDDEN)

    val state by mapSettingsComponent.mapSettingsState.collectAsState(initial = MapSettingState())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_map),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            DialogHandler(
                dialogState = state.mapDialogState,
                onClose = mapSettingsComponent::closeDialog,
                onResult = {
                    mapSettingsComponent.apply {
                        modify(it)
                        closeDialog()
                    }
                }
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
        VerticalSpacer(16.dp)
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
            DefaultLocationDialog(
                defaultLocationState = dialogState,
                onClose = onClose,
                onResult = onResult
            )
        }

        is None -> {}
    }
}