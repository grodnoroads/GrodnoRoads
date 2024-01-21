package com.egoriku.grodnoroads.root.di

import android.content.Context
import com.egoriku.grodnoroads.datastore.dataStore
import com.egoriku.grodnoroads.guidance.screen.di.guidanceUiModule
import com.egoriku.grodnoroads.shared.geolocation.AndroidLocationService
import com.egoriku.grodnoroads.shared.geolocation.LocationService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun initKoin(context: Context) {
    startKoin {
        androidContext(context)
        modules(appModule())
    }
}

internal actual val platformDataStoreModule = module {
    includes(guidanceUiModule)
    single { get<Context>().dataStore() }
    singleOf(::AndroidLocationService) { bind<LocationService>() }
}