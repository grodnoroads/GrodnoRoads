package com.egoriku.grodnoroads.shared.persistent.map.mapstyle

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

private val IS_SHOW_TRAFFIC_JAM_APPEARANCE = booleanPreferencesKey("is_show_traffic_jam_appearance")
private val GOOGLE_MAP_STYLE = stringPreferencesKey("google_map_style")

val Preferences.trafficJamOnMap: Boolean
    get() = this[IS_SHOW_TRAFFIC_JAM_APPEARANCE] ?: false

val Preferences.googleMapStyle: Style
    get() = Style.entries
        .firstOrNull { it.type == this[GOOGLE_MAP_STYLE] }
        ?: Style.Detailed

fun MutablePreferences.updateTrafficJamAppearance(value: Boolean) {
    this[IS_SHOW_TRAFFIC_JAM_APPEARANCE] = value
}

fun MutablePreferences.updateGoogleMapStyle(value: String) {
    this[GOOGLE_MAP_STYLE] = value
}