package com.egoriku.grodnoroads.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.edit
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "settings.preferences_pb"

fun createDataStore(
    producePath: () -> String,
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = null,
    migrations = emptyList(),
    produceFile = { producePath().toPath() },
)

suspend fun <T> DataStore<Preferences>.put(key: String, value: T) {
    edit {
        when (value) {
            is Int -> it[intPreferencesKey(key)] = value
            is Long -> it[longPreferencesKey(key)] = value
            is Double -> it[doublePreferencesKey(key)] = value
            is Float -> it[floatPreferencesKey(key)] = value
            is Boolean -> it[booleanPreferencesKey(key)] = value
            is String -> it[stringPreferencesKey(key)] = value
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }
}

suspend inline fun DataStore<Preferences>.edit(
    noinline transform: suspend MutablePreferences.() -> Unit
) = edit(transform)