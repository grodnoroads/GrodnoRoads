package com.egoriku.grodnoroads.shared.persistent.onboarding

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

private val SHOW_ONBOARDING = booleanPreferencesKey("show_onboarding")

val Preferences.showOnboarding: Boolean
    get() = this[SHOW_ONBOARDING] ?: true

fun MutablePreferences.completeOnboarding() {
    this[SHOW_ONBOARDING] = false
}