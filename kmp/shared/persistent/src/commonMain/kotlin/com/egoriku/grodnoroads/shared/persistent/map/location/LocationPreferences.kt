package com.egoriku.grodnoroads.shared.persistent.map.location

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

private val DEFAULT_CITY = stringPreferencesKey("default_city")

val Preferences.defaultCity: City
    get() = City.toCity(this[DEFAULT_CITY] ?: City.Grodno.cityName)

fun MutablePreferences.updateDefaultCity(value: String) {
    this[DEFAULT_CITY] = value
}