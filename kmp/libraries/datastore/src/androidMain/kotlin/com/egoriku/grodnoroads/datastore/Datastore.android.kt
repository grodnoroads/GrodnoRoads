package com.egoriku.grodnoroads.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences

fun Context.dataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        dataStoreFile(dataStoreFileName).absolutePath
    }
)