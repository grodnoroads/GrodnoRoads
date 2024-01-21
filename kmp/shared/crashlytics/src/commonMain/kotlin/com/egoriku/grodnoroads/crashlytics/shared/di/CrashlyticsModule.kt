package com.egoriku.grodnoroads.crashlytics.shared.di

import com.egoriku.grodnoroads.crashlytics.shared.CrashlyticsTracker
import com.egoriku.grodnoroads.crashlytics.shared.CrashlyticsTrackerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val crashlyticsModule = module {
    singleOf(::CrashlyticsTrackerImpl) { bind<CrashlyticsTracker>() }
}