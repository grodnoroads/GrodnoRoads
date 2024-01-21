package com.egoriku.grodnoroads.shared.persistent.alert

import androidx.datastore.preferences.core.*

const val DEFAULT_ALERT_DISTANCE_IN_CITY = 600
const val DEFAULT_ALERT_DISTANCE_OUTSIDE_CITY = 1000

private val ALERTS_AVAILABILITY = booleanPreferencesKey("alerts_feature_availability")
private val ALERTS_VOICE_ALERT_AVAILABILITY = booleanPreferencesKey("alerts_voice_alert_availability")

private val ALERTS_VOLUME_LEVEL = stringPreferencesKey("alerts_volume_level")
private val ALERTS_DISTANCE_IN_CITY = intPreferencesKey("alerts_distance_in_city")
private val ALERTS_DISTANCE_OUTSIDE_CITY = intPreferencesKey("alerts_distance_outside_city")

private val ALERTS_NOTIFY_STATIONARY_CAMERAS = booleanPreferencesKey("alerts_notify_stationary_cameras")
private val ALERTS_NOTIFY_MEDIUM_SPEED_CAMERAS = booleanPreferencesKey("alerts_notify_medium_speed_cameras")
private val ALERTS_NOTIFY_MOBILE_CAMERAS = booleanPreferencesKey("alerts_notify_mobile_cameras")
private val ALERTS_NOTIFY_TRAFFIC_POLICE_EVENTS = booleanPreferencesKey("alerts_notify_traffic_police_events")
private val ALERTS_NOTIFY_INCIDENT_EVENTS = booleanPreferencesKey("alerts_notify_incident_events")
private val ALERTS_NOTIFY_CAR_CRASH_EVENTS = booleanPreferencesKey("alerts_notify_car_crash_events")
private val ALERTS_NOTIFY_TRAFFIC_JAM_EVENTS = booleanPreferencesKey("alerts_notify_traffic_jam_events")
private val ALERTS_NOTIFY_WILD_ANIMALS_EVENTS = booleanPreferencesKey("alerts_notify_wild_animals_events")

val Preferences.alertsEnabled: Boolean
    get() = this[ALERTS_AVAILABILITY] ?: true

val Preferences.alertsVoiceAlertEnabled: Boolean
    get() = this[ALERTS_VOICE_ALERT_AVAILABILITY] ?: true

val Preferences.alertsVolumeLevel: VolumeLevel
    get() = VolumeLevel.entries
        .firstOrNull { it.levelName == this[ALERTS_VOLUME_LEVEL] }
        ?: VolumeLevel.Default

val Preferences.alertsDistanceInCity: Int
    get() = this[ALERTS_DISTANCE_IN_CITY] ?: DEFAULT_ALERT_DISTANCE_IN_CITY

val Preferences.alertsDistanceOutsideCity: Int
    get() = this[ALERTS_DISTANCE_OUTSIDE_CITY] ?: DEFAULT_ALERT_DISTANCE_OUTSIDE_CITY

val Preferences.isNotifyStationaryCameras: Boolean
    get() = this[ALERTS_NOTIFY_STATIONARY_CAMERAS] ?: true

val Preferences.isNotifyMediumSpeedCameras: Boolean
    get() = this[ALERTS_NOTIFY_MEDIUM_SPEED_CAMERAS] ?: true

val Preferences.isNotifyMobileCameras: Boolean
    get() = this[ALERTS_NOTIFY_MOBILE_CAMERAS] ?: true

val Preferences.isNotifyTrafficPolice: Boolean
    get() = this[ALERTS_NOTIFY_TRAFFIC_POLICE_EVENTS] ?: true

val Preferences.isNotifyRoadIncidents: Boolean
    get() = this[ALERTS_NOTIFY_INCIDENT_EVENTS] ?: true

val Preferences.isNotifyCarCrash: Boolean
    get() = this[ALERTS_NOTIFY_CAR_CRASH_EVENTS] ?: true

val Preferences.isNotifyTrafficJam: Boolean
    get() = this[ALERTS_NOTIFY_TRAFFIC_JAM_EVENTS] ?: true

val Preferences.isNotifyWildAnimals: Boolean
    get() = this[ALERTS_NOTIFY_WILD_ANIMALS_EVENTS] ?: true

fun MutablePreferences.updateAlertsAvailability(value: Boolean) {
    this[ALERTS_AVAILABILITY] = value
}

fun MutablePreferences.updateAlertsVoiceAlertAvailability(value: Boolean) {
    this[ALERTS_VOICE_ALERT_AVAILABILITY] = value
}

fun MutablePreferences.updateAlertsVolumeInfo(value: String) {
    this[ALERTS_VOLUME_LEVEL] = value
}

fun MutablePreferences.updateAlertsDistanceInCity(value: Int) {
    this[ALERTS_DISTANCE_IN_CITY] = value
}

fun MutablePreferences.updateAlertsDistanceOutsideCity(value: Int) {
    this[ALERTS_DISTANCE_OUTSIDE_CITY] = value
}

fun MutablePreferences.updateNotifyStationaryCameras(value: Boolean) {
    this[ALERTS_NOTIFY_STATIONARY_CAMERAS] = value
}

fun MutablePreferences.updateNotifyMediumSpeedCameras(value: Boolean) {
    this[ALERTS_NOTIFY_MEDIUM_SPEED_CAMERAS] = value
}

fun MutablePreferences.updateNotifyMobileCameras(value: Boolean) {
    this[ALERTS_NOTIFY_MOBILE_CAMERAS] = value
}

fun MutablePreferences.updateNotifyTrafficPolice(value: Boolean) {
    this[ALERTS_NOTIFY_TRAFFIC_POLICE_EVENTS] = value
}

fun MutablePreferences.updateNotifyRoadIncidents(value: Boolean) {
    this[ALERTS_NOTIFY_INCIDENT_EVENTS] = value
}

fun MutablePreferences.updateNotifyCarCrash(value: Boolean) {
    this[ALERTS_NOTIFY_CAR_CRASH_EVENTS] = value
}

fun MutablePreferences.updateNotifyTrafficJam(value: Boolean) {
    this[ALERTS_NOTIFY_TRAFFIC_JAM_EVENTS] = value
}

fun MutablePreferences.updateNotifyWildAnimals(value: Boolean) {
    this[ALERTS_NOTIFY_WILD_ANIMALS_EVENTS] = value
}