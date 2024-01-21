package com.egoriku.grodnoroads.root.di

import com.egoriku.grodnoroads.datastore.dataStore
import com.egoriku.grodnoroads.shared.geolocation.IosLocationService
import com.egoriku.grodnoroads.shared.geolocation.LocationService
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(modules = appModule())
    }
}

internal actual val platformDataStoreModule = module {
    single { dataStore() }
    singleOf(::IosLocationService) { bind<LocationService>() }
}