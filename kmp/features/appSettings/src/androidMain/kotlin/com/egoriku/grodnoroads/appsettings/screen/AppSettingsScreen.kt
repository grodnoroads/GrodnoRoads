package com.egoriku.grodnoroads.appsettings.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponent
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponentPreview
import com.egoriku.grodnoroads.appsettings.screen.ui.section.PrivacyPolicySection
import com.egoriku.grodnoroads.appsettings.screen.ui.section.SocialNetworkSection
import com.egoriku.grodnoroads.appsettings.screen.ui.section.VersionSection
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.common.ui.list.SettingsItem
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.components.FeatureFlags
import com.egoriku.grodnoroads.shared.models.Page

@Composable
fun AppSettingsScreen(
    contentPadding: PaddingValues,
    settingsComponent: AppSettingsComponent
) {
    SettingsUi(
        contentPadding = contentPadding,
        appVersion = settingsComponent.appVersion,
        onSettingClick = settingsComponent::open
    )
}

@Composable
private fun SettingsUi(
    contentPadding: PaddingValues,
    appVersion: String,
    onSettingClick: (Page) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(contentPadding)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsHeader(
                title = stringResource(R.string.settings_category_main),
                paddingValues = PaddingValues(start = 24.dp, bottom = 4.dp),
            )
            SettingsItem(
                icon = Icons.Filled.Style,
                text = stringResource(R.string.settings_section_appearance),
                onClick = {
                    onSettingClick(Page.Appearance)
                }
            )
            SettingsItem(
                icon = Icons.Filled.Map,
                text = stringResource(R.string.settings_section_map),
                onClick = {
                    onSettingClick(Page.MapSettings)
                }
            )
            SettingsItem(
                icon = Icons.Default.Notifications,
                text = stringResource(R.string.settings_section_alerts),
                onClick = {
                    onSettingClick(Page.Alerts)
                }
            )

            SettingsHeader(title = stringResource(R.string.settings_category_other))
            SettingsItem(
                icon = Icons.Filled.NewReleases,
                text = stringResource(R.string.settings_section_changelog),
                onClick = {
                    onSettingClick(Page.Changelog)
                }
            )

            if (FeatureFlags.settingsNextFeaturesEnabled) {
                SettingsItem(
                    icon = Icons.Filled.Build,
                    text = stringResource(R.string.settings_section_next_features),
                    onClick = {
                        onSettingClick(Page.NextFeatures)
                    }
                )
            }
            SettingsItem(
                icon = Icons.AutoMirrored.Filled.Help,
                text = stringResource(R.string.settings_section_faq),
                onClick = {
                    onSettingClick(Page.FAQ)
                }
            )
            WeightSpacer()
            SocialNetworkSection()
            VersionSection(appVersion = appVersion)
            PrivacyPolicySection()
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun AppSettingsScreenPreview() = GrodnoRoadsM3ThemePreview {
    AppSettingsScreen(
        settingsComponent = AppSettingsComponentPreview(),
        contentPadding = PaddingValues()
    )
}
