package com.egoriku.grodnoroads.shared.persistent.map.drivemode

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey

const val DEFAULT_MAP_ZOOM_IN_CITY = 14.8f
const val DEFAULT_MAP_ZOOM_OUT_CITY = 13.0f

private val MAP_ZOOM_IN_CITY = floatPreferencesKey("map_zoom_in_city")
private val MAP_ZOOM_OUTSIDE_CITY = floatPreferencesKey("map_zoom_outside_city")

val Preferences.mapZoomInCity: Float
    get() = this[MAP_ZOOM_IN_CITY] ?: DEFAULT_MAP_ZOOM_IN_CITY

val Preferences.mapZoomOutCity: Float
    get() = this[MAP_ZOOM_OUTSIDE_CITY] ?: DEFAULT_MAP_ZOOM_OUT_CITY

fun MutablePreferences.updateMapZoomInCity(value: Float) {
    this[MAP_ZOOM_IN_CITY] = value
}

fun MutablePreferences.updateMapZoomOutsideCity(value: Float) {
    this[MAP_ZOOM_OUTSIDE_CITY] = value
}