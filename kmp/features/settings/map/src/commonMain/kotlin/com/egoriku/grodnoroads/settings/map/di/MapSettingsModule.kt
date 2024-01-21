package com.egoriku.grodnoroads.settings.map.di

import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStoreFactory
import org.koin.dsl.module

val mapSettingsModule = module {
    factory {
        MapSettingsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}