package com.egoriku.grodnoroads.appsettings.domain

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.models.Page

@Stable
interface AppSettingsComponent {

    val appVersion: String

    fun open(page: Page)
}