package com.egoriku.grodnoroads.shared.persistent.appearance

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

private val APP_THEME = intPreferencesKey("app_theme")
private val KEEP_SCREEN_ON = booleanPreferencesKey("keep_screen_on")

val Preferences.appTheme: Theme
    get() = Theme.fromOrdinal(this[APP_THEME] ?: Theme.System.theme)

fun MutablePreferences.updateAppTheme(theme: Int) {
    this[APP_THEME] = theme
}

val Preferences.keepScreenOn: Boolean
    get() = this[KEEP_SCREEN_ON] ?: false

fun MutablePreferences.updateKeepScreenOn(enabled: Boolean) {
    this[KEEP_SCREEN_ON] = enabled
}
