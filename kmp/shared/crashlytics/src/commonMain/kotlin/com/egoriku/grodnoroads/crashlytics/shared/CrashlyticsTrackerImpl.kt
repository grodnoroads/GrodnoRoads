package com.egoriku.grodnoroads.crashlytics.shared

import com.egoriku.grodnoroads.logger.logD
import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics

internal class CrashlyticsTrackerImpl(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : CrashlyticsTracker {

    override fun recordException(exception: Throwable) {
        firebaseCrashlytics.recordException(exception).also {
            logD(exception.message.toString())
        }
    }
}