package com.egoriku.grodnoroads.crashlytics.shared.config

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics

object CrashlyticsConfig {

    fun isCollectionEnabled(value: Boolean) {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(value)
    }
}