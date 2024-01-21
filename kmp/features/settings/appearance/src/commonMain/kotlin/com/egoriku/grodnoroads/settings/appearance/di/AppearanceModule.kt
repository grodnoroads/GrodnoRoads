package com.egoriku.grodnoroads.settings.appearance.di

import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStoreFactory
import org.koin.dsl.module

val appearanceModule = module {
    factory {
        AppearanceStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}