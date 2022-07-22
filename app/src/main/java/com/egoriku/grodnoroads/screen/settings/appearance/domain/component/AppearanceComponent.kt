package com.egoriku.grodnoroads.screen.settings.appearance.domain.component

import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.None
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language.*
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme.*
import kotlinx.coroutines.flow.Flow

interface AppearanceComponent {

    val state: Flow<State>

    fun modify(preference: AppearancePref)
    fun update(preference: AppearancePref)
    fun closeDialog()

    data class State(
        val dialogState: AppearanceDialogState = None,
        val appearanceState: AppearanceState = AppearanceState()
    )

    data class AppearanceState(
        val appTheme: AppTheme = AppTheme(),
        val appLanguage: AppLanguage = AppLanguage(),
    )

    sealed interface AppearancePref {
        data class AppTheme(
            val current: Theme = System,
            val values: List<Theme> = listOf(System, Light, Dark)
        ) : AppearancePref

        data class AppLanguage(
            val current: Language = Russian,
            val values: List<Language> = listOf(Russian, Belarusian, English)
        ) : AppearancePref
    }

    sealed interface AppearanceDialogState {
        data class ThemeDialogState(val themes: AppTheme) : AppearanceDialogState
        data class LanguageDialogState(val languages: AppLanguage) : AppearanceDialogState

        object None : AppearanceDialogState
    }
}