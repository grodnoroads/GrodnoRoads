package com.egoriku.grodnoroads.crashlytics.shared

interface CrashlyticsTracker {

    fun recordException(exception: Throwable)
}