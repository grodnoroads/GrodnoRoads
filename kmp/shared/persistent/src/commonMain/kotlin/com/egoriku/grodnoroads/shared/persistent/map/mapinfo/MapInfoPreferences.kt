package com.egoriku.grodnoroads.shared.persistent.map.mapinfo

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

private val IS_SHOW_STATIONARY_CAMERAS = booleanPreferencesKey("is_show_stationary_cameras")
private val IS_SHOW_MEDIUM_SPEED_CAMERAS = booleanPreferencesKey("is_show_medium_speed_cameras")
private val IS_SHOW_MOBILE_CAMERAS = booleanPreferencesKey("is_show_mobile_cameras")
private val IS_SHOW_TRAFFIC_POLICE_EVENTS = booleanPreferencesKey("is_show_traffic_police_events")
private val IS_SHOW_INCIDENT_EVENTS = booleanPreferencesKey("is_show_incident_events")
private val IS_SHOW_CAR_CRASH_EVENTS = booleanPreferencesKey("is_show_car_crash_events")
private val IS_SHOW_TRAFFIC_JAM_EVENTS = booleanPreferencesKey("is_show_traffic_jam_events")
private val IS_SHOW_WILD_ANIMALS_EVENTS = booleanPreferencesKey("is_show_wild_animals_events")

val Preferences.isShowStationaryCameras: Boolean
    get() = this[IS_SHOW_STATIONARY_CAMERAS] ?: true

val Preferences.isShowMediumSpeedCameras: Boolean
    get() = this[IS_SHOW_MEDIUM_SPEED_CAMERAS] ?: true

val Preferences.isShowMobileCameras: Boolean
    get() = this[IS_SHOW_MOBILE_CAMERAS] ?: true

val Preferences.isShowTrafficPolice: Boolean
    get() = this[IS_SHOW_TRAFFIC_POLICE_EVENTS] ?: true

val Preferences.isShowRoadIncidents: Boolean
    get() = this[IS_SHOW_INCIDENT_EVENTS] ?: true

val Preferences.isShowCarCrash: Boolean
    get() = this[IS_SHOW_CAR_CRASH_EVENTS] ?: true

val Preferences.isShowTrafficJam: Boolean
    get() = this[IS_SHOW_TRAFFIC_JAM_EVENTS] ?: true

val Preferences.isShowWildAnimals: Boolean
    get() = this[IS_SHOW_WILD_ANIMALS_EVENTS] ?: true

fun MutablePreferences.updateStationaryCameras(value: Boolean) {
    this[IS_SHOW_STATIONARY_CAMERAS] = value
}

fun MutablePreferences.updateMediumSpeedCameras(value: Boolean) {
    this[IS_SHOW_MEDIUM_SPEED_CAMERAS] = value
}

fun MutablePreferences.updateMobileCameras(value: Boolean) {
    this[IS_SHOW_MOBILE_CAMERAS] = value
}

fun MutablePreferences.updateTrafficPolice(value: Boolean) {
    this[IS_SHOW_TRAFFIC_POLICE_EVENTS] = value
}

fun MutablePreferences.updateRoadIncidents(value: Boolean) {
    this[IS_SHOW_INCIDENT_EVENTS] = value
}

fun MutablePreferences.updateCarCrash(value: Boolean) {
    this[IS_SHOW_CAR_CRASH_EVENTS] = value
}

fun MutablePreferences.updateTrafficJam(value: Boolean) {
    this[IS_SHOW_TRAFFIC_JAM_EVENTS] = value
}

fun MutablePreferences.updateWildAnimals(value: Boolean) {
    this[IS_SHOW_WILD_ANIMALS_EVENTS] = value
}